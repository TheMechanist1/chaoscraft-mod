package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.util.PositionRange;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class BlockPositionInput extends InputNeuron {
    private static final String BLOCK_ID = "BLOCK_ID";
    public String attributeId;
    public String attributeValue;
    public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        PositionRange adustedPositionRange = this.positionRange.orientToEntity(this.nNet.entity);
        float value = -1;
        for(float x = adustedPositionRange.minX; x < adustedPositionRange.maxX; x++){
            for(float y = adustedPositionRange.minY; y < adustedPositionRange.maxY; y++){
                for(float z = adustedPositionRange.minZ; z < adustedPositionRange.maxZ; z++){
                    BlockPos pos = new BlockPos(x,y,z);
                    Block block = this.nNet.entity.world.getBlockState(pos).getBlock();
                    switch(attributeId){
                        case(BLOCK_ID):
                            int blockId = Block.getIdFromBlock(block);
                            if(blockId == Integer.parseInt(attributeValue)){
                                value = 1;
                            }
                        break;
                    }
                }
            }
        }
        return value;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        positionRange = new PositionRange();
        positionRange.parseData((JSONObject) jsonObject.get("positionRange"));
    }

}