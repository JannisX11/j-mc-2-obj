package org.jmc.models;

import org.jmc.BlockData;
import org.jmc.geom.Transform;
import org.jmc.geom.UV;
import org.jmc.geom.Vertex;
import org.jmc.registry.NamespaceID;
import org.jmc.threading.ChunkProcessor;
import org.jmc.threading.ThreadChunkDeligate;


/**
 * Model for cocoa plants
 */
public class CocoaPlant extends BlockModel
{

	@Override
	protected NamespaceID[] getMtlSides(BlockData data, int biome)
	{
		int growth = Integer.parseInt(data.state.get("age"));
		
		NamespaceID[] abbrMtls = materials.get(data.state,biome);

		NamespaceID[] mtlSides = new NamespaceID[6];
		mtlSides[0] = abbrMtls[growth];
		mtlSides[1] = abbrMtls[growth];
		mtlSides[2] = abbrMtls[growth];
		mtlSides[3] = abbrMtls[growth];
		mtlSides[4] = abbrMtls[growth];
		mtlSides[5] = abbrMtls[growth];
		return mtlSides;
	}

	
	@Override
	public void addModel(ChunkProcessor obj, ThreadChunkDeligate chunks, int x, int y, int z, BlockData data, int biome)
	{
		String dir = data.state.get("facing");
		int growth = Integer.parseInt(data.state.get("age"));
				
		/*
		 The model is rendered facing south and then rotated  
		*/
		Transform rotate = new Transform();
		Transform translate = new Transform();
		Transform rt;

		switch (dir)
		{
			case "south": rotate = Transform.rotation(0, 180, 0); break;
			case "west": rotate = Transform.rotation(0, -90, 0); break;
			case "north": rotate = Transform.rotation(0, 0, 0); break;
			case "east": rotate = Transform.rotation(0, 90, 0); break;
		}
		translate = Transform.translation(x, y, z);
		rt = translate.multiply(rotate);


		UV[] uvTop, uvSide;
		UV[][] uvSides;

		if (growth == 2)
		{
			uvTop = new UV[] { new UV(0,9/16f), new UV(7/16f,9/16f), new UV(7/16f,1), new UV(0,1) };
			uvSide = new UV[] { new UV(7/16f,3/16f), new UV(15/16f,3/16f), new UV(15/16f,12/16f), new UV(7/16f,12/16f) };
			uvSides = new UV[][] { uvTop, uvSide, uvSide, uvSide, uvSide, uvTop };

			addBox(obj,
					-0.25f, -0.3125f, -0.4375f,
					0.25f, 0.25f, 0.0625f,
					rt, 
					getMtlSides(data,biome), 
					uvSides, 
					null);
		}
		else if (growth == 1)
		{
			uvTop = new UV[] { new UV(0,10/16f), new UV(6/16f,10/16f), new UV(6/16f,1), new UV(0,1) };
			uvSide = new UV[] { new UV(9/16f,5/16f), new UV(15/16f,5/16f), new UV(15/16f,12/16f), new UV(9/16f,12/16f) };
			uvSides = new UV[][] { uvTop, uvSide, uvSide, uvSide, uvSide, uvTop };
			
			addBox(obj,
					-0.1875f, -0.1875f, -0.4375f, 
					0.1875f, 0.25f, -0.0625f, 
					rt, 
					getMtlSides(data,biome), 
					uvSides, 
					null);
		}
		else
		{
			uvTop = new UV[] { new UV(0,12/16f), new UV(4/16f,12/16f), new UV(4/16f,1), new UV(0,1) };
			uvSide = new UV[] { new UV(11/16f,7/16f), new UV(15/16f,7/16f), new UV(15/16f,12/16f), new UV(11/16f,12/16f) };
			uvSides = new UV[][] { uvTop, uvSide, uvSide, uvSide, uvSide, uvTop };
			
			addBox(obj,
					-0.125f, -0.0625f, -0.4375f,
					0.125f, 0.25f, -0.1875f, 
					rt, 
					getMtlSides(data,biome), 
					uvSides, 
					null);
		}
		
		
		Vertex[] vertices = new Vertex[4];
		uvSide = new UV[] { new UV(8/16f,12/16f), new UV(1,12/16f), new UV(1,1), new UV(8/16f,1) };
		
		vertices[0] = new Vertex(0, 0.25f, 0);
		vertices[1] = new Vertex(0, 0.25f, -0.5f);
		vertices[2] = new Vertex(0, 0.5f, -0.5f);
		vertices[3] = new Vertex(0, 0.5f, 0);
		obj.addFace(vertices, uvSide, rt, materials.get(data.state,biome)[growth]);
	}

}
