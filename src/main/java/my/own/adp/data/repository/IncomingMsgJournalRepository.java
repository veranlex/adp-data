package my.own.adp.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import my.own.adp.data.entity.IncomingMsgJournal;

import java.util.Collection;
import java.util.List;

@Repository
public interface IncomingMsgJournalRepository extends PagingAndSortingRepository<IncomingMsgJournal, String> {

    @Query(value = "SELECT * FROM integration.incoming_msg_journal ij WHERE ij.status IN :statuses", nativeQuery = true)
    List<IncomingMsgJournal> getMsgJournalByStatuses(@Param("statuses") Collection<String> statuses, Pageable pageable);

    @Query(value = "SELECT * FROM integration.incoming_msg_journal ij WHERE ij.id IN :ids", nativeQuery = true)
    List<IncomingMsgJournal> getMsgJournalByIds(@Param("ids") Collection<String> ids, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE integration.incoming_msg_journal SET status = :status, " +
            "error_message = :error, send_counter= :sendCounter WHERE id = :id", nativeQuery = true)
    void updateStatus(@Param("id") String id,
                      @Param("status") String status,
                      @Param("error") String error,
                      @Param("sendCounter") Integer sendCounter);


}
