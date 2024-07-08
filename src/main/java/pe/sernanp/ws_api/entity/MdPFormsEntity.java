package pe.sernanp.ws_api.entity;

import lombok.Data;
import pe.sernanp.ws_api.model.FormsMPV;
import pe.sernanp.ws_api.model.PayFormMPV;

@Data
public class MdPFormsEntity {
    private FormsMPV form1;
    private FormsMPV form2;
    private FormsMPV form3;
    private FormsMPV form5;
    private FormsMPV form7;
    private FormsMPV form8;
    private FormsMPV form9;
    private FormsMPV form10;
    private PayFormMPV formPago;

    public void setForm1(FormsMPV form1) {
        if (form1 != null)
            form1.setForm("form1");
        this.form1 = form1;
    }

    public void setForm2(FormsMPV form2) {
        if (form2 != null)
            form2.setForm("form2");
        this.form2 = form2;
    }

    public void setForm3(FormsMPV form3) {
        if (form3 != null)
            form3.setForm("form3");
        this.form3 = form3;
    }

    public void setForm5(FormsMPV form5) {
        if (form5 != null)
            form5.setForm("form5");
        this.form5 = form5;
    }

    public void setForm7(FormsMPV form7) {
        if (form7 != null)
            form7.setForm("form7");
        this.form7 = form7;
    }

    public void setForm8(FormsMPV form8) {
        if (form8 != null)
            form8.setForm("form8");
        this.form8 = form8;
    }

    public void setForm9(FormsMPV form9) {
        if (form9 != null)
            form9.setForm("form9");
        this.form9 = form9;
    }

    public void setForm10(FormsMPV form10) {
        if (form10 != null)
            form10.setForm("form10");
        this.form10 = form10;
    }
}
