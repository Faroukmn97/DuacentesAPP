package com.example.duacentes.interfaces;


import com.example.duacentes.models.CheckpointModel;
import com.example.duacentes.models.ExternalresourceModel;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;
import com.example.duacentes.models.ToolModel;

public interface iCommunicates_Fragments {
    /**
     * esta interface se encarga de realizar la comunicacion entre la lista de personas y el detalle
     * En la clase principleModel se implementa Serializable para poder transportar un objeteo a otro)
     *
     * @param principleModel se transportara un objeto de tipo principleModel
     */

    public void SendPrincipleforPrincipleDetailLModel(PrincipleModel principleModel);

    public void SendDetailPrincipleforGuidelineModel(PrincipleModel principleModel);

    public void SendGuidelineforGuidelineDetailModel(GuidelineModel guidelineModel, PrincipleModel principleModel);

    public void SendDetailGuidelineforCheckpoints(GuidelineModel guidelineModel, PrincipleModel principleModel);

    public void SendCheckpointforCheckpointDetail(CheckpointModel checkpointModel , PrincipleModel principleModel);

    public void SendISPrincipleModel(PrincipleModel principleModel);

    public void SendISGuidelineModel(GuidelineModel guidelineModel, PrincipleModel principleModel);

    public void SendToolModel(ToolModel toolModel);

    public void SendToolModeltoSearchResourceDetail(ToolModel toolModel);

    public void SendExternalResourceModel(ExternalresourceModel externalresourceModel);


    public void ReturnISGuidelineModel(PrincipleModel principleModel);


}
