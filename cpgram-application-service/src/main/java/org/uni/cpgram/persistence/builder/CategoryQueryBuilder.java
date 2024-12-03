package org.uni.cpgram.persistence.builder;

public class CategoryQueryBuilder {

    public static String buildSearchCategoriesQuery() {
        return "SELECT * FROM public.m_cpgram_categories WHERE " +
                "code = ? OR description = ? OR orgcode = ? OR parent = ? OR stage = ? OR " +
                "descriptionhindi = ? OR monitoringcode = ? OR mappingcode = ? OR fieldcode = ? OR " +
                "destination = ? OR isactive = ?";
    }

    public static String buildSearchCategoriesByTextQuery() {
        return "SELECT * FROM public.m_cpgram_categories WHERE " +
                "CAST(code AS TEXT) ILIKE ? OR description ILIKE ? OR orgcode ILIKE ? OR " +
                "CAST(parent AS TEXT) ILIKE ? OR CAST(stage AS TEXT) ILIKE ? OR descriptionhindi ILIKE ? OR " +
                "CAST(monitoringcode AS TEXT) ILIKE ? OR mappingcode ILIKE ? OR CAST(fieldcode AS TEXT) ILIKE ? OR " +
                "destination ILIKE ? OR isactive ILIKE ?";
    }
}
