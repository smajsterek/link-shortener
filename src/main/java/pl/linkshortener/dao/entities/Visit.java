package pl.linkshortener.dao.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private Link link;

    @Column
    private LocalDateTime timeOfVisit;

    @ManyToOne
    @JoinColumn(name = "query")
    private GeoIP geoIP;

}
