package com.cap.dojo.graphql.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cap.dojo.graphql.entity.Article;
import com.cap.dojo.graphql.entity.Commande;
import com.merapar.graphql.base.TypedValueMap;

@Service
public class CommandeDataFetcher {
	
	public Iterable<Commande> getCommandesClient(final TypedValueMap typedValueMap) {
		final long idClient = typedValueMap.get("id");
		
		List<Commande> commandes = new ArrayList<>();
		if (idClient == 5) {
			Commande commande = new Commande();
			commande.setId(1L);
			commande.setReference("REF123456");
			Date dateCommande = new Date();
			commande.setDateCreation(dateCommande);
			commande.setReference("REF123456");
			List<Article> articles = new ArrayList<>();
			Article article1 = new Article();
			article1.setNom("pull");
			article1.setCouleur("blanc");
			article1.setTaille(42);
			Article article2 = new Article();
			articles.add(article1);
			articles.add(article2);
			commande.setArticles(articles);
			commandes.add(commande);
		}
		return commandes;
	}
}
