package com.campraca.twitterapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
public class PortfolioEntity {
    @Id
    @Column(name = "idportfolio")
    private Integer idPortfolio;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "twitter_user_name")
    private String twitterUserName;
    private String title;
    private String tittle;
}
