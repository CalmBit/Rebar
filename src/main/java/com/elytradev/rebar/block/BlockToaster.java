/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017:
 * 	Ethan Brooks (CalmBit),
 * 	and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
			DisplayToastMessage toast = DisplayToastMessage.builder("Toast!")
					.setSubtitle("Toasty.")
					.setTiming(5000L)
					.setTexture("rebar:textures/gui/toast.png")
					.setTitleColor(-5290496)
					.setSubtitleColor(-1)
					.setTextureX(0)
					.setTextureY(0)
					.create(Rebar.NETWORK_CONTEXT);

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
