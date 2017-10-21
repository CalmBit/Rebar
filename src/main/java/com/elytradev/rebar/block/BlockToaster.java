package com.elytradev.rebar.block;

import com.elytradev.concrete.utilpackets.DisplayToastMessage;
import com.elytradev.rebar.Rebar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToaster extends BlockBase {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockToaster() {
        super(Material.IRON, "toaster");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            DisplayToastMessage toast = new DisplayToastMessage(Rebar.NETWORK_CONTEXT,
                    "Toast!", "Toasty.", 5000L, "rebar:textures/gui/toast.png",-5290496, -1, 0, 0);
            toast.sendTo(playerIn);
        }

        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);

        if(facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState[] surround = {
                worldIn.getBlockState(pos.north()),
                worldIn.getBlockState(pos.south()),
                worldIn.getBlockState(pos.west()),
                worldIn.getBlockState(pos.east())
            };

            EnumFacing enumfacing = state.getValue(FACING);

            switch(enumfacing) {
                case NORTH:
                    if(surround[0].isFullBlock() && !surround[1].isFullBlock())
                        enumfacing = EnumFacing.SOUTH;
                    break;
                case SOUTH:
                    if(surround[1].isFullBlock() && !surround[0].isFullBlock())
                        enumfacing = EnumFacing.NORTH;
                    break;
                case WEST:
                    if(surround[2].isFullBlock() && !surround[3].isFullBlock())
                        enumfacing = EnumFacing.EAST;
                    break;
                case EAST:
                    if(surround[3].isFullBlock() && !surround[2].isFullBlock())
                        enumfacing = EnumFacing.WEST;
                    break;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }


}
