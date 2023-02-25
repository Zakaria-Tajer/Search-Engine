package com.frontier.url.domains;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "websites")
@Data
@AllArgsConstructor
public class Websites {

    @Id
    @Field(type = FieldType.Keyword)

    private String websiteId;
    @Field(type = FieldType.Text)

    private String websiteUrl;
    @Field(type = FieldType.Boolean)
    private boolean isFetched;


}
