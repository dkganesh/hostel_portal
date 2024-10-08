package com.hostel.portal.repository.pass_related;

import com.hostel.portal.entity.pass_related.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry,Long> {
}
