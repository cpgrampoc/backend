package org.uni.cpgram.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class VesselRepository {

    private final JdbcTemplate jdbcTemplate;

    // Inject the JdbcTemplate for the vessel database using @Qualifier
    @Autowired
    public VesselRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
   /* public void printDataSourceInfo() {
        // Get the DataSource associated with the JdbcTemplate
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection()) {
                // Print out the connection URL (which includes the database name)
                System.out.println("Connected to database: " + connection.getMetaData().getURL());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("DataSource is not initialized.");
        }
    }*//* public void printDataSourceInfo() {
        // Get the DataSource associated with the JdbcTemplate
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection()) {
                // Print out the connection URL (which includes the database name)
                System.out.println("Connected to database: " + connection.getMetaData().getURL());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("DataSource is not initialized.");
        }
    }*/

    public List<Map<String, Object>> getVesselDetails() {

        String sql = """
                SELECT
                    vd.category,
                    vd.gross_tonnage,
                    vd.registered_tonnage,
                    vd.vessel_type,
                    rb.address_of_owner,
                    rb.name_of_owner,
                    rb.name_of_vessel,
                    rb.port_of_registry,
                    rb.registration_no,
                    rb.status,
                    rb.year_of_registry,
                    tr.date_hour_of_registry,
                    tr.name_of_person_title_derived,
                    tr.no_of_shares_affected,
                    en.bhp,
                    en.description,
                    en.diameter_of_cylinder_in_inches,
                    en.engine_no,
                    en.ihp,
                    en.made_by,
                    en.nhp,
                    en.no_of_cylinders_per_set,
                    en.no_of_seats,
                    en.strokes_in_inches,
                    en.surface_jet,
                    en.type_of_engine,
                    pr.date_of_registration,
                    pr.propeler_type,
                    pr.propulsion_geared_or_direct_driven,
                    pr.propulsion_type,
                    pr.registering_authority,
                    pr.rpm,
                    pr.speed_of_vessel,
                    bp.grate_area,
                    bp.made_of_material,
                    bp.no_of_boiler,
                    bp.total_heating_surface,
                    bp.type_ofboiler,
                    bp.working_pressure,
                    bp.year_of_make,
                    vp.breadth_extrenme,
                    vp.build,
                    vp.builder_name_address,
                    vp.depth_of_underside_of_deckamid_ships,
                    vp.description,
                    vp.length_overall,
                    vp.no_of_bulkheads,
                    vp.no_of_decks,
                    vp.stren,
                    vp.type_of_hull,
                    vp.year_of_build
                FROM
                    iwt_vessle_description_t vd
                LEFT JOIN
                    iwt_registration_book_t rb ON vd.uuid = rb.uuid
                LEFT JOIN
                    iwt_transaction_t tr ON vd.uuid = tr.uuid
                LEFT JOIN
                    iwt_engine_t en ON vd.uuid = en.uuid
                LEFT JOIN
                    iwt_boilers_t bp ON vd.uuid = bp.uuid
                LEFT JOIN
                    iwt_propulsion_t pr ON vd.uuid = pr.uuid
                LEFT JOIN
                    iwt_vessle_particulars_t vp ON vd.uuid = vp.uuid
                """;


        // Execute the query and return the result as a list of maps
        return jdbcTemplate.queryForList(sql);

    }
}
