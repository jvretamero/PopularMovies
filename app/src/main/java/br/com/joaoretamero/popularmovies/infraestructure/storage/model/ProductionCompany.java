package br.com.joaoretamero.popularmovies.infraestructure.storage.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "production_company", id = "_id")
public class ProductionCompany extends Model {

    @Column(name = "production_company_id")
    public int productionCompanyId;

    @Column(name = "name")
    public String name;

    public ProductionCompany() {
        super();
    }

    public static ProductionCompany findOneFromProductionCompanyId(int productionCompanyId) {
        return new Select()
                .from(ProductionCompany.class)
                .where("production_company_id = ?", productionCompanyId)
                .executeSingle();
    }

    public static List<ProductionCompany> findAllFromMovie(Long movieId) {
        return new Select()
                .from(ProductionCompany.class)
                .as("target_model")
                .innerJoin(MovieProductionCompany.class)
                .as("join_model")
                .on("join_model.production_company = target_model._id")
                .where("join_model.movie = ?", movieId)
                .orderBy("target_model.name")
                .execute();
    }

    public static void bulkInsert(List<ProductionCompany> productionCompanyList) {
        ActiveAndroid.beginTransaction();
        try {
            for (ProductionCompany productionCompany : productionCompanyList) {
                productionCompany.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
