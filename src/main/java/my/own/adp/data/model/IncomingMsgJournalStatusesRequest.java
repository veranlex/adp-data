package my.own.adp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncomingMsgJournalStatusesRequest {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    @Size(min = 1)
    private List<String> statuses;
}
