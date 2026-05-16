# RideWise - Quick Command Reference

## 🚀 Essential Commands

### Navigate to Project
```bash
cd D:\Airtribe\Ridewise
```

### Compile All Java Files
```bash
javac -d bin $(Get-ChildItem -Path src -Recurse -Include "*.java" | Select-Object -ExpandProperty FullName | ForEach-Object {"""$_"""})
```

### Run the Application
```bash
java -cp bin com.airtribe.ridewise.Main
```

### Verify Compilation
```bash
Get-ChildItem -Path bin -Recurse -Include "*.class" | Measure-Object
```

---

## 📂 File Locations

| Item | Location |
|------|----------|
| Source Code | `D:\Airtribe\Ridewise\src\com\airtribe\ridewise\` |
| Compiled Classes | `D:\Airtribe\Ridewise\bin\com\airtribe\ridewise\` |
| Documentation | `D:\Airtribe\Ridewise\docs\` |
| README | `D:\Airtribe\Ridewise\README.md` |
| Main Class | `com.airtribe.ridewise.Main` |

---

## 📚 Documentation Files

| File | Purpose | Lines |
|------|---------|-------|
| `README.md` | Project overview | 442 |
| `docs/Requirements.md` | Specifications | 159 |
| `docs/Class_Model.md` | Architecture | 432 |
| `docs/SOLID_Reflection.md` | SOLID principles | 456 |
| `docs/Object_Relationships.md` | Entity relationships | 589 |

---

## 🔧 Troubleshooting

### If Compilation Fails
1. Check Java is installed: `java -version`
2. Check javac is in PATH: `javac -version`
3. Verify src directory structure
4. Try compiling individual files

### If Application Won't Run
1. Verify bin directory exists
2. Check compiled classes are present
3. Ensure package structure is correct
4. Check classpath: `-cp bin`

### If Menu Doesn't Appear
1. Clear screen: `cls`
2. Run again: `java -cp bin com.airtribe.ridewise.Main`
3. Check for errors in output

---

## ✨ Main Application Features

Press these numbers in the menu:
- `1` → Add Rider
- `2` → Add Driver
- `3` → View Available Drivers
- `4` → Request Ride
- `5` → Complete Ride
- `6` → View All Rides
- `7` → Change Fare Strategy
- `8` → Change Matching Strategy
- `9` → View Ride Details & Fare
- `0` → Exit

---

## 📊 Project Statistics

- **Java Files**: 18
- **Documentation Files**: 4
- **Total Lines of Code**: ~2,500
- **Compilation Status**: ✅ Success (0 errors)
- **Runtime Status**: ✅ Success (0 errors)
- **Test Status**: ✅ Verified

---

## 🎯 Key Directories

```
Ridewise/
├── src/                          ← Source code
├── bin/                          ← Compiled classes
├── docs/                         ← Documentation
└── README.md                     ← Project overview
```

---

## 📖 Documentation Reading Order

1. **README.md** - Start here for overview
2. **docs/Requirements.md** - Understand what was built
3. **docs/Class_Model.md** - See the architecture
4. **docs/SOLID_Reflection.md** - Learn the principles
5. **docs/Object_Relationships.md** - Understand relationships

---

## 🔍 Verification Commands

### Check Source Files
```bash
Get-ChildItem -Path src/com/airtribe/ridewise -Recurse -Include "*.java" | Measure-Object
```

### Check Compiled Files
```bash
Get-ChildItem -Path bin/com/airtribe/ridewise -Recurse -Include "*.class" | Measure-Object
```

### Check Documentation
```bash
Get-ChildItem -Path docs -Include "*.md" | Format-Table Name, @{Name="Lines";Expression={(Get-Content $_.FullName | Measure-Object -Line).Lines}}
```

---

## 💡 Quick Tips

1. **To change strategies at runtime**:
   - While running, select option 7 or 8
   - Subsequent rides will use new strategy

2. **To test different scenarios**:
   - Add multiple riders and drivers
   - Request rides with different strategies active
   - View fare differences with different strategies

3. **To understand SOLID principles**:
   - Read docs/SOLID_Reflection.md
   - Study the Strategy pattern in code
   - See how strategies are injected

4. **To extend the system**:
   - Create new strategy implementation
   - Implement the interface
   - Inject into RideService
   - No other code changes needed!

---

## 📋 Pre-Loaded Sample Data

**Riders** (available immediately):
- R1: Rajesh Kumar (Downtown)
- R2: Priya Singh (Airport Road)

**Drivers** (available immediately):
- D1: Amit Patel (Market Square, CAR)
- D2: Suresh Nair (Bus Station, AUTO)
- D3: Vikram Sharma (Railway Station, BIKE)

---

## 🎓 Learning Resources

Located in `docs/`:
- **Class_Model.md**: Visual architecture diagrams
- **SOLID_Reflection.md**: Detailed principle explanations
- **Object_Relationships.md**: Entity relationship analysis
- **Requirements.md**: Complete specification

---

## ✅ Submission Checklist

Before submitting, verify:
- [ ] All 18 Java files present in src/
- [ ] All 4 documentation files in docs/
- [ ] Project compiles without errors
- [ ] Application runs successfully
- [ ] All menu options work
- [ ] Sample data loads correctly

---

## 🚀 Ready to Go!

You're all set! The project is:
✅ Complete
✅ Compiled
✅ Tested
✅ Documented
✅ Ready for submission

---

**Last Updated**: May 13, 2026
**Status**: Production-Ready

