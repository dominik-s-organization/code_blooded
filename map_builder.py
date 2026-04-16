# map_builder.py
# Bővített pályaépítő a Hókotrók prototípushoz, entitások elhelyezésével

# 1. Csomópontok közötti kapcsolatok (Gráf élek)
connections = [
    ("J1", "J2"),
    ("J2", "J3"),
    ("J1", "J4"),
    ("J4", "J5"),
    ("J2", "J5")
]

# 2. Pályán elhelyezendő entitások (típus, azonosító, kezdőcsomópont)
entities = [
    ("plower", "plower_1", "J1"),
    ("plower", "plower_2", "J5"),
    ("bus", "bus_1", "J4"),
    ("car", "car_1", "J2"),
    ("cleaner", "cleaner_1", "J1") # Játékos (takarító)
]

def generate_map(connections_list, entities_list, output_filename="test_map.txt"):
    junctions = set()
    
    # Kigyűjtjük az összes egyedi csomópontot
    for u, v in connections_list:
        junctions.add(u)
        junctions.add(v)
    
    with open(output_filename, "w", encoding="utf-8") as f:
        f.write("# --- Csomopontok (Junctions) letrehozasa ---\n")
        for j in sorted(junctions):
            f.write(f"create_junction {j}\n")
        
        f.write("\n# --- Savok (Lanes) letrehozasa es osszekotese ---\n")
        lane_id = 1
        for u, v in connections_list:
            lane_name = f"lane_{lane_id}"
            f.write(f"create_lane {lane_name} {u} {v}\n")
            lane_id += 1
            
        f.write("\n# --- Entitasok (Jarmuvek, Jatekosok) elhelyezese ---\n")
        for ent_type, ent_id, pos in entities_list:
            f.write(f"place_entity {ent_type} {ent_id} {pos}\n")
            
    print(f"Kész! A térkép és a kezdeti játékállás kigenerálva ide: {output_filename}")

if __name__ == "__main__":
    generate_map(connections, entities)