package DonArDato;

public class SpinnerItem {

    private String idData;
    private String descriptionData;

    public SpinnerItem(String idData, String descriptionData){
        this.idData = idData;
        this.descriptionData = descriptionData;
    }
//
    public String getIdData(){return this.idData;}
    public String getDescriptionData(){return this.descriptionData;}
}
