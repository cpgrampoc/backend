package org.uni.cpgram.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.uni.cpgram.persistence.dto.CategoryDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private CategoryDTO searchFields;
    private String searchText;
}
