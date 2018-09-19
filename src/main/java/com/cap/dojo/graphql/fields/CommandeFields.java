package com.cap.dojo.graphql.fields;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.dojo.graphql.service.CommandeDataFetcher;
import com.merapar.graphql.GraphQlFields;
import com.merapar.graphql.base.GraphQlFieldsHelper;

import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;

@Component
public class CommandeFields implements GraphQlFields {

	/**
	 * Service d'accès à la base de données
	 */
	@Autowired
	private CommandeDataFetcher commandeDF;
	
	@Override
	public List<GraphQLFieldDefinition> getQueryFields() {
		List<GraphQLFieldDefinition> queryFields = new ArrayList<>();
		
		
		GraphQLInputObjectField idField = GraphQLInputObjectField.newInputObjectField().name("id").type(Scalars.GraphQLLong).build();
		
		GraphQLInputObjectType cientId = GraphQLInputObjectType.newInputObject()
				.name("cientId")
				.description("L'id client pour trouver ses commandes")
				.field(idField)
				.build();

		GraphQLArgument argumentClientId = GraphQLArgument.newArgument().name(GraphQlFieldsHelper.FILTER).type(new GraphQLNonNull(cientId)).build();
		
		GraphQLObjectType typeArticle = GraphQLObjectType.newObject()
				.name("article")
				.description("un article de la boutique")
				.field(GraphQLFieldDefinition.newFieldDefinition().name("nom").description("Nom de l'article").type(Scalars.GraphQLString))
				.field(GraphQLFieldDefinition.newFieldDefinition().name("couleur").description("Couleur de l'article").type(Scalars.GraphQLString))
				.field(GraphQLFieldDefinition.newFieldDefinition().name("taille").description("Taille de l'article").type(Scalars.GraphQLInt))
				.build(); 
		
		GraphQLObjectType typeCommande = GraphQLObjectType.newObject()
				.name("commande")
				.description("la commande faite par un client")
				.field(GraphQLFieldDefinition.newFieldDefinition().name("id").description("Id de la commande").type(Scalars.GraphQLLong))
				.field(GraphQLFieldDefinition.newFieldDefinition().name("reference").description("Référence de la commande").type(Scalars.GraphQLString))
//				.field(GraphQLFieldDefinition.newFieldDefinition().name("dateCreation").description("Date de création de la commande").type(Scalars.GraphQLString))
				.field(GraphQLFieldDefinition.newFieldDefinition().name("articles").description("La liste des articles de la commande").type(new GraphQLList(typeArticle)))
				.build(); 

		
		GraphQLFieldDefinition commandesParClientQuery = GraphQLFieldDefinition.newFieldDefinition().name("getCommandesParClient")
				.description("Récupère la liste des commandes pour un client")
				.type(new GraphQLList(typeCommande))
				.argument(argumentClientId)
				.dataFetcher(env -> commandeDF.getCommandesClient(GraphQlFieldsHelper.getFilterMap(env)))
//				.dataFetcher(new DataFetcher() {
//					@Override
//					public Object get(DataFetchingEnvironment environment) {
//						return commandeDataFetcher
//								.getCommandeClient(GraphQlFieldsHelper.getFilterMap(environment));
//					}
//				})
				.build();

		queryFields.add(commandesParClientQuery);

//		getAllCommandes(queryFields);
//		getCommandeId(queryFields);
		return queryFields;
	}

	@Override
	public List<GraphQLFieldDefinition> getMutationFields() {
		List<GraphQLFieldDefinition> mutationFields = new ArrayList<>();
		return mutationFields;
	}

}
