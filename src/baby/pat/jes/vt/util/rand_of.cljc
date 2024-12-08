(ns baby.pat.jes.vt.util.rand-of
  (:require [clojure.set]
            [clojure.spec.gen.alpha :as gen]
            [medley.core]
            [orchestra.core #?(:clj :refer :cljs :refer-macros) [defn-spec]]))

(def nato-alphabet (set ["alfa" "bravo" "charlie" "delta" "echo" "foxtrot" "golf" "hotel" "india" "juliett" "kilo" "lima" "mike" "november" "oscar" "papa" "quebec" "romeo" "sierra" "tango" "uniform" "victor" "whiskey" "xray" "yankee" "zulu"]))

(def animals (set ["Alligator" "Anteater" "Ape" "Armadillo" "Baboon" "Bat" "Bear" "Beetle" "Camel" "Centipede" "Chameleon" "Cheetah" "Clownfish" "Cockatoo" "Crane" "Crocodile" "Deer" "Drill" "Duck" "Eagle" "Elephant" "Elk" "Flamingo" "Fox" "Giraffe" "Gorilla" "Hamster" "Hawk" "Hedgehog" "Hippo" "Hippopotamus" "Horse" "Hummingbird" "Hyena" "Iguana" "Impala" "Jaguar" "Kangaroo" "Koala" "Lemur" "Leopard" "Lion" "Lionfish" "Lizard" "Lynx" "Mole" "Monkey" "Newt" "Opossum" "Orangutan" "Ostrich" "Owl" "Panda" "Panther" "Parrot" "Peacock" "Pelican" "Penguin" "Pigeon" "Platypus" "Quail" "Rabbit" "Rattlesnake" "Reindeer" "Rhinoceros" "Rooster" "Scorpion" "Seal" "Skunk" "Snake" "Sparrow" "Squirrel" "Swan" "Toucan" "Tiger" "Turkey" "Turtle" "Vulture" "Walrus" "Wolf" "Woodpecker" "Yak" "Zebra"]))

(def ing-verbs (set ["watering" "cataloging" "hunting" "wanting" "holding" "taping" "integrating" "worrying" "loving" "spending" "fitting" "bating" "risking" "normalizing" "restructuring" "costing" "programming" "touching" "towing" "altering" "marketing" "yelling" "crushing" "beholding" "agreeing" "fencing" "sparkling" "wiping" "sparking" "slaying" "copying" "melting" "appraising" "complaining" "leading" "telling" "crashing" "subtracting" "grabbing" "wrecking" "thanking" "forming" "answering" "overhearing" "wriggling" "ringing" "admitting" "bruising" "making" "pumping" "bumping" "dragging" "consisting" "accepting" "dropping" "smelling" "recognizing" "facing" "deciding" "deserting" "riding" "ensuring" "frightening" "shading" "flapping" "washing" "completing" "heaping" "snoring" "draining" "clothing" "detailing" "initiating" "dispensing" "diagnosing" "paddling" "singing" "promising" "handling" "planing" "separating" "thriving" "shrinking" "scrubbing" "confusing" "spotting" "scattering" "noticing" "upgrading" "piloting" "estimating" "showing" "reigning" "folding" "contracting" "blushing" "broadcasting" "speaking" "slipping" "squashing" "pecking" "hanging" "returning" "receiving" "landing" "injecting" "fleeing" "cheering" "sniffing" "sleeping" "clinging" "breeding" "searching" "carving" "meaning" "attaching" "affording" "nesting" "undergoing" "passing" "entertaining" "longing" "enjoying" "fighting" "wrestling" "unfastening" "drawing" "supposing" "greasing" "producing" "spinning" "asking" "projecting" "enduring" "adopting" "fancying" "conducting" "conceiving" "guessing" "mating" "overthrowing" "regulating" "determining" "bearing" "devising" "abiding" "hearing" "rhyming" "retrieving" "servicing" "preaching" "rubbing" "clarifying" "wobbling" "groaning" "speeding" "filling" "repairing" "pining" "launching" "shaping" "breathing" "spoiling" "living" "recruiting" "proposing" "pedaling" "operating" "trying" "licensing" "discovering" "overdoing" "rinsing" "camping" "displaying" "muddling" "pricking" "processing" "counseling" "consolidating" "shivering" "numbering" "removing" "sliding" "referring" "representing" "inspecting" "assisting" "enhancing" "administering" "identifying" "enacting" "skipping" "shaking" "spelling" "selecting" "shopping" "causing" "reflecting" "photographing" "withstanding" "evaluating" "breaking" "visiting" "creeping" "feeding" "loading" "graduating" "combing" "tickling" "catching" "dividing" "squealing" "fixing" "floating" "logging" "chewing" "carrying" "hurting" "sacking" "expressing" "getting" "forbidding" "sawing" "moaning" "grinding" "ruining" "hurrying" "balancing" "exploding" "spilling" "welcoming" "eliminating" "acceding" "classifying" "smiling" "assuring" "settling" "scheduling" "perceiving" "shooting" "reconciling" "faxing" "executing" "decaying" "marrying" "stinging" "investigating" "caring" "questioning" "proving" "rescuing" "filming" "laying" "tracing" "alerting" "remaining" "activating" "interesting" "boasting" "imagining" "putting" "controlling" "disliking" "addressing" "solving" "shining" "wringing" "fading" "accelerating" "establishing" "curling" "attacking" "guaranteeing" "deceiving" "patting" "applauding" "noting" "pressing" "kneeling" "hitting" "presiding" "repeating" "prescribing" "arising" "adding" "sewing" "educating" "manipulating" "belonging" "giving" "participating" "doubting" "misunderstanding" "following" "trotting" "writing" "damming" "correlating" "plugging" "attending" "retiring" "understanding" "walking" "pinpointing" "braking" "soaking" "remembering" "slinging" "borrowing" "rocking" "allowing" "obeying" "coiling" "cycling" "untidying" "stirring" "baking" "saying" "rotting" "learning" "governing" "confessing" "reminding" "utilizing" "dramatizing" "knowing" "puncturing" "shearing" "suggesting" "achieving" "heading" "punching" "greeting" "gazing" "swinging" "occurring" "sneezing" "creating" "hiding" "stitching" "promoting" "changing" "snowing" "taking" "lying" "reading" "responding" "bouncing" "multiplying" "conserving" "challenging" "losing" "sowing" "fooling" "informing" "matching" "dealing" "detecting" "rating" "screwing" "interlaying" "phoning" "binding" "stoping" "weighing" "editing" "letting" "publicizing" "preparing" "financing" "checking" "exciting" "firing" "bombing" "restoring" "eating" "sending" "blotting" "amusing" "disagreeing" "innovating" "relying" "correcting" "confronting" "judging" "reducing" "arguing" "stamping" "parking" "naming" "doing" "chopping" "banging" "engineering" "trusting" "describing" "delegating" "cheating" "lending" "mistaking" "squeaking" "winding" "comparing" "adapting" "cracking" "scolding" "scraping" "buzzing" "existing" "improvising" "praising" "splitting" "distributing" "curing" "mapping" "progressing" "forgiving" "dressing" "manning" "polishing" "smoking" "tugging" "being"]))
(def male-first-names (set ["James" "John" "Robert" "Michael" "William" "David" "Richard" "Charles" "Joseph" "Thomas" "Christopher" "Daniel" "Paul" "Mark" "Donald" "George" "Kenneth" "Steven" "Edward" "Brian" "Ronald" "Anthony" "Kevin" "Jason" "Matthew" "Gary" "Timothy" "Jose" "Larry" "Jeffrey" "Frank" "Scott" "Eric" "Stephen" "Andrew" "Raymond" "Gregory" "Joshua" "Jerry" "Dennis" "Walter" "Patrick" "Peter" "Harold" "Douglas" "Henry" "Carl" "Arthur" "Ryan" "Roger" "Joe" "Juan" "Jack" "Albert" "Jonathan" "Justin" "Terry" "Gerald" "Keith" "Samuel" "Willie" "Ralph" "Lawrence" "Nicholas" "Roy" "Benjamin" "Bruce" "Brandon" "Adam" "Harry" "Fred" "Wayne" "Billy" "Steve" "Louis" "Jeremy" "Aaron" "Randy" "Howard" "Eugene" "Carlos" "Russell" "Bobby" "Victor" "Martin" "Ernest" "Phillip" "Todd" "Jesse" "Craig" "Alan" "Shawn" "Clarence" "Sean" "Philip" "Chris" "Johnny" "Earl" "Jimmy" "Antonio" "Danny" "Bryan" "Tony" "Luis" "Mike" "Stanley" "Leonard" "Nathan" "Dale" "Manuel" "Rodney" "Curtis" "Norman" "Allen" "Marvin" "Vincent" "Glenn" "Jeffery" "Travis" "Jeff" "Chad" "Jacob" "Lee" "Melvin" "Alfred" "Kyle" "Francis" "Bradley" "Jesus" "Herbert" "Frederick" "Ray" "Joel" "Edwin" "Don" "Eddie" "Ricky" "Troy" "Randall" "Barry" "Alexander" "Bernard" "Mario" "Leroy" "Francisco" "Marcus" "Micheal" "Theodore" "Clifford" "Miguel" "Oscar" "Jay" "Jim" "Tom" "Calvin" "Alex" "Jon" "Ronnie" "Bill" "Lloyd" "Tommy" "Leon" "Derek" "Warren" "Darrell" "Jerome" "Floyd" "Leo" "Alvin" "Tim" "Wesley" "Gordon" "Dean" "Greg" "Jorge" "Dustin" "Pedro" "Derrick" "Dan" "Lewis" "Zachary" "Corey" "Herman" "Maurice" "Vernon" "Roberto" "Clyde" "Glen" "Hector" "Shane" "Ricardo" "Sam" "Rick" "Lester" "Brent" "Ramon" "Charlie" "Tyler" "Gilbert" "Gene" "Marc" "Reginald" "Ruben" "Brett" "Angel" "Nathaniel" "Rafael" "Leslie" "Edgar" "Milton" "Raul" "Ben" "Chester" "Cecil" "Duane" "Franklin" "Andre" "Elmer" "Brad" "Gabriel" "Ron" "Mitchell" "Roland" "Arnold" "Harvey" "Jared" "Adrian" "Karl" "Cory" "Claude" "Erik" "Darryl" "Jamie" "Neil" "Jessie" "Christian" "Javier" "Fernando" "Clinton" "Ted" "Mathew" "Tyrone" "Darren" "Lonnie" "Lance" "Cody" "Julio" "Kelly" "Kurt" "Allan" "Nelson" "Guy" "Clayton" "Hugh" "Max" "Dwayne" "Dwight" "Armando" "Felix" "Jimmie" "Everett" "Jordan" "Ian" "Wallace" "Ken" "Bob" "Jaime" "Casey" "Alfredo" "Alberto" "Dave" "Ivan" "Johnnie" "Sidney" "Byron" "Julian" "Isaac" "Morris" "Clifton" "Willard" "Daryl" "Ross" "Virgil" "Andy" "Marshall" "Salvador" "Perry" "Kirk" "Sergio" "Marion" "Tracy" "Seth" "Kent" "Terrance" "Rene" "Eduardo" "Terrence" "Enrique" "Freddie" "Wade"]))

(def female-first-names (set ["Mary" "Patricia" "Linda" "Barbara" "Elizabeth" "Jennifer" "Maria" "Susan" "Margaret" "Dorothy" "Lisa" "Nancy" "Karen" "Betty" "Helen" "Sandra" "Donna" "Carol" "Ruth" "Sharon" "Michelle" "Laura" "Sarah" "Kimberly" "Deborah" "Jessica" "Shirley" "Cynthia" "Angela" "Melissa" "Brenda" "Amy" "Anna" "Rebecca" "Virginia" "Kathleen" "Pamela" "Martha" "Debra" "Amanda" "Stephanie" "Carolyn" "Christine" "Marie" "Janet" "Catherine" "Frances" "Ann" "Joyce" "Diane" "Alice" "Julie" "Heather" "Teresa" "Doris" "Gloria" "Evelyn" "Jean" "Cheryl" "Mildred" "Katherine" "Joan" "Ashley" "Judith" "Rose" "Janice" "Kelly" "Nicole" "Judy" "Christina" "Kathy" "Theresa" "Beverly" "Denise" "Tammy" "Irene" "Jane" "Lori" "Rachel" "Marilyn" "Andrea" "Kathryn" "Louise" "Sara" "Anne" "Jacqueline" "Wanda" "Bonnie" "Julia" "Ruby" "Lois" "Tina" "Phyllis" "Norma" "Paula" "Diana" "Annie" "Lillian" "Emily" "Robin" "Peggy" "Crystal" "Gladys" "Rita" "Dawn" "Connie" "Florence" "Tracy" "Edna" "Tiffany" "Carmen" "Rosa" "Cindy" "Grace" "Wendy" "Victoria" "Edith" "Kim" "Sherry" "Sylvia" "Josephine" "Thelma" "Shannon" "Sheila" "Ethel" "Ellen" "Elaine" "Marjorie" "Carrie" "Charlotte" "Monica" "Esther" "Pauline" "Emma" "Juanita" "Anita" "Rhonda" "Hazel" "Amber" "Eva" "Debbie" "April" "Leslie" "Clara" "Lucille" "Jamie" "Joanne" "Eleanor" "Valerie" "Danielle" "Megan" "Alicia" "Suzanne" "Michele" "Gail" "Bertha" "Darlene" "Veronica" "Jill" "Erin" "Geraldine" "Lauren" "Cathy" "Joann" "Lorraine" "Lynn" "Sally" "Regina" "Erica" "Beatrice" "Dolores" "Bernice" "Audrey" "Yvonne" "Annette" "June" "Samantha" "Marion" "Dana" "Stacy" "Ana" "Renee" "Ida" "Vivian" "Roberta" "Holly" "Brittany" "Melanie" "Loretta" "Yolanda" "Jeanette" "Laurie" "Katie" "Kristen" "Vanessa" "Alma" "Sue" "Elsie" "Beth" "Jeanne" "Vicki" "Carla" "Tara" "Rosemary" "Eileen" "Terri" "Gertrude" "Lucy" "Tonya" "Ella" "Stacey" "Wilma" "Gina" "Kristin" "Jessie" "Natalie" "Agnes" "Vera" "Willie" "Charlene" "Bessie" "Delores" "Melinda" "Pearl" "Arlene" "Maureen" "Colleen" "Allison" "Tamara" "Joy" "Georgia" "Constance" "Lillie" "Claudia" "Jackie" "Marcia" "Tanya" "Nellie" "Minnie" "Marlene" "Heidi" "Glenda" "Lydia" "Viola" "Courtney" "Marian" "Stella" "Caroline" "Dora" "Jo" "Vickie" "Mattie" "Terry" "Maxine" "Irma" "Mabel" "Marsha" "Myrtle" "Lena" "Christy" "Deanna" "Patsy" "Hilda" "Gwendolyn" "Jennie" "Nora" "Margie" "Nina" "Cassandra" "Leah" "Penny" "Kay" "Priscilla" "Naomi" "Carole" "Brandy" "Olga" "Billie" "Dianne" "Tracey" "Leona" "Jenny" "Felicia" "Sonia" "Miriam" "Velma" "Becky" "Bobbie" "Violet" "Kristina" "Toni" "Misty" "Mae" "Shelly" "Daisy" "Ramona" "Sherri" "Erika" "Katrina" "Claire"]))

(def numbers (set ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine" "ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"]))


(def numbers-in-10s (set ["twenty" "thrity" "fourty" "fifty" "sixty" "seventy" "eighty" "ninety"]))

(def nations (set ["afghanistan" "albania" "algeria" "argentina" "australia" "austria" "bangladesh" "belgium" "bolivia" "botswana" "brazil" "bulgaria" "cambodia" "cameroon" "canada" "chile" "china" "colombia" "croatia" "cuba" "denmark" "ecuador" "egypt" "england" "estonia" "ethiopia" "fiji" "finland" "france" "germany" "ghana" "greece" "guatemala" "haiti" "honduras" "hungary" "iceland" "india" "indonesia" "iran" "iraq" "ireland" "israel" "italy" "jamaica" "japan" "jordan" "kenya" "kuwait" "laos" "latvia" "lebanon" "libya" "lithuania" "madagascar" "malaysia" "mali" "malta" "mexico" "mongolia" "morocco" "mozambique" "namibia" "nepal" "netherlands" "nicaragua" "nigeria" "norway" "pakistan" "panama" "paraguay" "peru" "philippines" "poland" "portugal" "romania" "russia" "saudi" "scotland" "senegal" "serbia" "singapore" "slovakia" "spain"  "sudan" "sweden" "switzerland" "syria" "taiwan" "thailand" "tonga" "tunisia" "turkey" "ukraine" "uruguay" "venezuela" "vietnam" "wales" "zambia" "zimbabwe"]))

(def colors (set ["aqua" "blue" "ivory" "cyan" "peach" "white" "silver" "purple" "magenta" "neon" "rose" "salmon" "tropical" "grey" "crimson" "sepia" "aquamarine" "orange" "violet" "gray" "goldenrod" "outerspace" "fuchsia" "cerise" "shadow" "azure" "yellow" "beige" "sherbet" "scarlet" "periwinkle" "pink" "lemon" "teal" "olive" "blush" "navy" "tumbleweed" "green" "red" "maroon" "brown" "tan" "lavender" "sand" "khaki" "mahogany" "turquoise" "charcoal" "indigo" "cerulean" "mauve"]))

(def last-names (set ["Abbott" "Acevedo" "Acosta" "Adams" "Adkins" "Aguilar" "Aguirre" "Ahmed" "Alexander" "Alfaro" "Ali" "Allen" "Allison" "Alvarado" "Alvarez" "Andersen" "Anderson" "Andrade" "Andrews" "Anthony" "Archer" "Arellano" "Arias" "Armstrong" "Arnold" "Arroyo" "Ashley" "Atkins" "Atkinson" "Austin" "Avalos" "Avery" "Avila" "Ayala" "Ayers" "Bailey" "Baker" "Baldwin" "Ball" "Ballard" "Banks" "Barajas" "Barber" "Barker" "Barnes" "Barnett" "Barr" "Barrera" "Barrett" "Barron" "Barry" "Bartlett" "Barton" "Bass" "Bates" "Bauer" "Bautista" "Baxter" "Bean" "Beard" "Beasley" "Beck" "Becker" "Beil" "Bell" "Beltran" "Bender" "Benitez" "Benjamin" "Bennett" "Benson" "Bentley" "Benton" "Berg" "Berger" "Bernal" "Bernard" "Berry" "Best" "Bishop" "Black" "Blackburn" "Blackwell" "Blair" "Blake" "Blanchard" "Blankenship" "Blevins" "Bond" "Bonilla" "Booker" "Boone" "Booth" "Bowen" "Bowers" "Bowman" "Boyd" "Boyer" "Boyle" "Bradford" "Bradley" "Bradshaw" "Brady" "Branch" "Brandt" "Bravo" "Brennan" "Brewer" "Bridges" "Briggs" "Brock" "Brooks" "Brown" "Browning" "Bruce" "Bryan" "Bryant" "Buchanan" "Buck" "Buckley" "Bullock" "Burch" "Burgess" "Burke" "Burnett" "Burns" "Burton" "Bush" "Butler" "Byrd" "Cabrera" "Cain" "Calderon" "Caldwell" "Calhoun" "Callahan" "Camacho" "Cameron" "Campbell" "Campos" "Cannon" "Cano" "Cantrell" "Cantu" "Cardenas" "Carey" "Carlson" "Carpenter" "Carr" "Carrillo" "Carroll" "Carson" "Carter" "Case" "Casey" "Castaneda" "Castillo" "Castro" "Cervantes" "Chambers" "Chan" "Chandler" "Chang" "Chapman" "Charles" "Chase" "Chavez" "Chen" "Cherry" "Choi" "Christensen" "Christian" "Chung" "Church" "Cisneros" "Clark" "Clarke" "Clay" "Clayton" "Clements" "Cline" "Cobb" "Cochran" "Coffey" "Cohen" "Cole" "Coleman" "Collier" "Collins" "Colon" "Combs" "Compton" "Conley" "Conner" "Conrad" "Contreras" "Conway" "Cook" "Cooper" "Copeland" "Cordova" "Corona" "Correa" "Cortes" "Cortez" "Costa" "Cox" "Craig" "Crane" "Crawford" "Crosby" "Cross" "Cruz" "Cuevas" "Cummings" "Cunningham" "Curry" "Curtis" "Dalton" "Daniel" "Daniels" "Daugherty" "Davenport" "David" "Davidson" "Davila" "Davis" "Dawson" "Day" "Dean" "Decker" "Dejesus" "Delacruz" "Delarosa" "Deleon" "Delgado" "Dennis" "Diaz" "Dickerson" "Dickson" "Dillon" "Dixon" "Dodson" "Dominguez" "Donaldson" "Donovan" "Dorsey" "Dougherty" "Douglas" "Doyle" "Drake" "Duarte" "Dudley" "Duffy" "Duke" "Duncan" "Dunlap" "Dunn" "Duran" "Durham" "Dyer" "Eaton" "Edwards" "Elliott" "Ellis" "Ellison" "English" "Enriquez" "Erickson" "Escobar" "Esparza" "Espinosa" "Espinoza" "Esquivel" "Estes" "Estrada" "Evans" "Everett" "Farley" "Farmer" "Farrell" "Faulkner" "Felix" "Ferguson" "Fernandez" "Fields" "Figueroa" "Finley" "Fischer" "Fisher" "Fitzgerald" "Fitzpatrick" "Fleming" "Fletcher" "Flores" "Flowers" "Floyd" "Flynn" "Foley" "Ford" "Foster" "Fowler" "Fox" "Francis" "Franco" "Frank" "Franklin" "Frazier" "Frederick" "Freeman" "French" "Friedman" "Frost" "Fry" "Frye" "Fuentes" "Fuller" "Gaines" "Galindo" "Gallagher" "Gallegos" "Galvan" "Garcia" "Gardner" "Garner" "Garrett" "Garrison" "Garza" "Gates" "Gentry" "George" "Gibbs" "Gibson" "Gilbert" "Giles" "Gill" "Gillespie" "Gilmore" "Glass" "Glenn" "Glover" "Golden" "Gomez" "Gonzales" "Gonzalez" "Good" "Goodman" "Goodwin" "Gordon" "Gould" "Graham" "Grant" "Graves" "Gray" "Green" "Greene" "Greer" "Gregory" "Griffin" "Griffith" "Grimes" "Gross" "Guerra" "Guerrero" "Guevara" "Gutierrez" "Guzman" "Hahn" "Hail" "Hale" "Haley" "Hall" "Hamilton" "Hammond" "Hampton" "Hancock" "Hanna" "Hansen" "Hanson" "Hardin" "Harding" "Hardy" "Harmon" "Harper" "Harrell" "Harrington" "Harris" "Harrison" "Hart" "Hartman" "Harvey" "Hawkins" "Hayden" "Hayes" "Haynes" "Heath" "Hebert" "Henderson" "Hendricks" "Hendrix" "Henry" "Hensley" "Henson" "Herman" "Hernandez" "Herrera" "Herring" "Hess" "Hester" "Hickman" "Hicks" "Higgins" "Hill" "Hines" "Hinton" "Ho" "Hobbs" "Hodge" "Hodges" "Hoffman" "Hogan" "Holland" "Holloway" "Holmes" "Holt" "Hood" "Hoover" "Hopkins" "Horn" "Horne" "Horton" "House" "Houston" "Howard" "Howe" "Howell" "Huang" "Hubbard" "Huber" "Hudson" "Huerta" "Huff" "Huffman" "Hughes" "Hull" "Humphrey" "Hunt" "Hunter" "Hurley" "Hurst" "Hutchinson" "Huynh" "Ibarra" "Ingram" "Jackson" "Jacobs" "Jacobson" "James" "Jaramillo" "Jarvis" "Jefferson" "Jenkins" "Jennings" "Jensen" "Jimenez" "Johns" "Johnson" "Johnston" "Jones" "Jordan" "Joseph" "Juarez" "Kane" "Kaur" "Keith" "Keller" "Kelley" "Kelly" "Kemp" "Kennedy" "Kent" "Kerr" "Khan" "Kim" "King" "Kirby" "Kirk" "Klein" "Kline" "Knapp" "Knight" "Knox" "Koch" "Kramer" "Krueger" "Lam" "Lamb" "Lambert" "Landry" "Lane" "Lang" "Lara" "Larsen" "Larson" "Lawrence" "Lawson" "Le" "Leach" "Leal" "Leblanc" "Lee" "Leon" "Leonard" "Lester" "Levy" "Lewis" "Li" "Lim" "Lin" "Lindsey" "Little" "Liu" "Livingston" "Lloyd" "Logan" "Long" "Lopez" "Love" "Lowe" "Lowery" "Lozano" "Lu" "Lucas" "Lucero" "Lugo" "Luna" "Lynch" "Lynn" "Lyons" "Macdonald" "Macias" "Mack" "Madden" "Maddox" "Magana" "Mahoney" "Maldonado" "Malone" "Mann" "Manning" "Marin" "Marks" "Marquez" "Marsh" "Marshall" "Martin" "Martinez" "Mason" "Massey" "Mata" "Mathews" "Mathis" "Matthews" "Maxwell" "May" "Mayer" "Maynard" "Mayo" "Mays" "Mcbride" "Mccall" "Mccann" "Mccarthy" "Mccarty" "Mcclain" "Mcclure" "Mcconnell" "Mccormick" "Mccoy" "Mccullough" "Mcdaniel" "Mcdonald" "Mcdowell" "Mcfarland" "Mcgee" "Mcguire" "Mcintosh" "Mcintyre" "Mckay" "Mckee" "Mckenzie" "Mckinney" "Mclaughlin" "Mclean" "Mcmahon" "Mcmillan" "Mcpherson" "Meadows" "Medina" "Medrano" "Mejia" "Melendez" "Melton" "Mendez" "Mendoza" "Mercado" "Merritt" "Meyer" "Meyers" "Meza" "Michael" "Middleton" "Miles" "Miller" "Mills" "Miranda" "Mitchell" "Molina" "Monroe" "Montes" "Montgomery" "Montoya" "Moody" "Moon" "Moore" "Mora" "Morales" "Moran" "Moreno" "Morgan" "Morris" "Morrison" "Morrow" "Morse" "Morton" "Moses" "Mosley" "Moss" "Moyer" "Mueller" "Mullen" "Mullins" "Munoz" "Murillo" "Murphy" "Murray" "Myers" "Nash" "Nava" "Navarro" "Neal" "Nelson" "Newman" "Newton" "Nguyen" "Nichols" "Nicholson" "Nielsen" "Nixon" "Noble" "Nolan" "Norman" "Norris" "Norton" "Novak" "Nunez" "Obrien" "Ochoa" "Oconnell" "Oconnor" "Odom" "Odonnell" "Oliver" "Olsen" "Olson" "Oneal" "Oneill" "Orozco" "Orr" "Ortega" "Ortiz" "Osborne" "Owen" "Owens" "Pace" "Pacheco" "Padilla" "Page" "Palacios" "Palmer" "Park" "Parker" "Parks" "Parra" "Parrish" "Parsons" "Patel" "Patrick" "Patterson" "Patton" "Paul" "Payne" "Pearson" "Peck" "Pena" "Pennington" "Peralta" "Perez" "Perkins" "Perry" "Person" "Peters" "Petersen" "Peterson" "Pham" "Phan" "Phelps" "Phillips" "Pierce" "Pineda" "Pittman" "Pitts" "Pollard" "Ponce" "Poole" "Pope" "Porter" "Portillo" "Potter" "Potts" "Powell" "Powers" "Pratt" "Preston" "Price" "Prince" "Proctor" "Pruitt" "Pugh" "Quinn" "Quintana" "Quintero" "Ramirez" "Ramos" "Ramsey" "Randall" "Randolph" "Rangel" "Rasmussen" "Ray" "Raymond" "Reed" "Reese" "Reeves" "Reid" "Reilly" "Reyes" "Reyna" "Reynolds" "Rhodes" "Rice" "Rich" "Richard" "Richards" "Richardson" "Richmond" "Riley" "Rios" "Rivas" "Rivera" "Rivers" "Roach" "Robbins" "Roberson" "Roberts" "Robertson" "Robinson" "Robles" "Rocha" "Rodgers" "Rodriguez" "Rogers" "Rojas" "Rollins" "Roman" "Romero" "Rosales" "Rosario" "Rosas" "Rose" "Ross" "Roth" "Rowe" "Rowland" "Roy" "Rubio" "Ruiz" "Rush" "Russell" "Russo" "Ryan" "Salas" "Salazar" "Salgado" "Salinas" "Sampson" "Sanchez" "Sanders" "Sandoval" "Sanford" "Santana" "Santiago" "Santos" "Saunders" "Savage" "Sawyer" "Schaefer" "Schmidt" "Schmitt" "Schneider" "Schroeder" "Schultz" "Schwartz" "Scott" "Sellers" "Serrano" "Sexton" "Shaffer" "Shah" "Shannon" "Sharp" "Shaw" "Shelton" "Shepard" "Shepherd" "Sheppard" "Sherman" "Shields" "Short" "Sierra" "Silva" "Simmons" "Simon" "Simpson" "Sims" "Singh" "Singleton" "Skinner" "Sloan" "Small" "Smith" "Snow" "Snyder" "Solis" "Solomon" "Sosa" "Soto" "Sparks" "Spears" "Spence" "Spencer" "Stafford" "Stanley" "Stanton" "Stark" "Steele" "Stein" "Stephens" "Stephenson" "Stevens" "Stevenson" "Stewart" "Stokes" "Stone" "Stout" "Strickland" "Strong" "Stuart" "Suarez" "Sullivan" "Summers" "Sutton" "Swanson" "Sweeney" "Tang" "Tanner" "Tapia" "Tate" "Taylor" "Terrell" "Terry" "Thomas" "Thompson" "Thornton" "Todd" "Torres" "Townsend" "Tran" "Travis" "Trejo" "Trevino" "Trujillo" "Truong" "Tucker" "Turner" "Tyler" "Underwood" "Valdez" "Valencia" "Valentine" "Valenzuela" "Vance" "Vang" "Vargas" "Vasquez" "Vaughan" "Vaughn" "Vazquez" "Vega" "Velasquez" "Velazquez" "Velez" "Ventura" "Villa" "Villalobos" "Villanueva" "Villarreal" "Villegas" "Vincent" "Vo" "Vu" "Wade" "Wagner" "Walker" "Wall" "Wallace" "Waller" "Walls" "Walsh" "Walter" "Walters" "Walton" "Wang" "Ward" "Ware" "Warner" "Warren" "Washington" "Waters" "Watkins" "Watson" "Watts" "Weaver" "Webb" "Weber" "Webster" "Weeks" "Weiss" "Welch" "Wells" "West" "Wheeler" "Whitaker" "White" "Whitehead" "Whitney" "Wiggins" "Wilcox" "Wiley" "Wilkerson" "Wilkins" "Wilkinson" "Williams" "Williamson" "Willis" "Wilson" "Winters" "Wise" "Wolf" "Wolfe" "Wong" "Wood" "Woodard" "Woods" "Woodward" "Wright" "Wu" "Wyatt" "Xiong" "Yang" "Yates" "Yoder" "York" "Young" "Yu" "Zamora" "Zavala" "Zhang" "Zimmerman" "Zuniga"]))

(def adverbs #{"wisely" "joyfully" "politely" "famously" "majestically" "coolly" "more" "quietly" "highly" "truthfully" "promptly" "righteously" "mechanically" "evenly" "keenly" "thoroughly" "dearly" "seldom" "lovingly" "properly" "scarily" "yearly" "smoothly" "questionably" "nearly" "sleepily" "powerfully" "bravely" "punctually" "urgently" "wearily" "physically" "very" "instantly" "yearningly" "lazily" "unnaturally" "queasily" "suddenly" "rarely" "voluntarily" "eventually" "fully" "gladly" "warmly" "crossly" "coaxingly" "queerly" "swiftly" "optimistically" "cruelly" "irritably" "excitedly" "wonderfully" "officially" "loosely" "quirkily" "reproachfully" "upside-down" "uselessly" "mockingly" "greatly" "awkwardly" "daintily" "anxiously" "not" "certainly" "verbally" "offensively" "intensely" "victoriously" "longingly" "mortally" "generally" "deliberately" "far" "carelessly" "obnoxiously" "mysteriously" "tensely" "meaningfully" "innocently" "weakly" "youthfully" "unabashedly" "violently" "triumphantly" "continually" "surprisingly" "shrilly" "partially" "soon" "equally" "patiently" "naturally" "honestly" "fatally" "reluctantly" "especially" "dreamily" "hopelessly" "restfully" "knavishly" "courageously" "painfully" "extremely" "elegantly" "frankly" "cautiously" "woefully" "vainly" "kindly" "lightly" "carefully" "helplessly" "upbeat" "vivaciously" "boastfully" "inquisitively" "energetically" "perfectly" "colorfully" "helpfully" "doubtfully" "tomorrow" "delightfully" "busily" "madly" "quickly" "cheerfully" "zestfully" "likely" "diligently" "oddly" "fervently" "tenderly" "loudly" "viciously" "deeply" "fondly" "truly" "shyly" "scarcely" "utterly" "rightfully" "freely" "happily" "exactly" "obediently" "unfortunately" "easily" "ultimately" "bleakly" "hastily" "enthusiastically" "vaguely" "cleverly" "generously" "loyally" "successfully" "daily" "healthily" "thoughtfully" "always" "often" "jealously" "wildly" "interestingly" "wrongly" "afterwards" "closely" "solidly" "solemnly" "monthly" "fairly" "vacantly" "greedily" "joyously" "even" "judgmentally" "adventurously" "frightfully" "gratefully" "bashfully" "potentially" "nicely" "miserably" "vastly" "fortunately" "abnormally" "sometimes" "seemingly" "zealously" "knowingly" "never" "terribly" "almost" "foolishly" "regularly" "yieldingly" "sympathetically" "positively" "fast" "stealthily" "ferociously" "furiously" "yawningly" "readily" "arrogantly" "lively" "frenetically" "nervously" "correctly" "immediately" "sweetly" "mostly" "rudely" "safely" "faithfully" "calmly" "separately" "strictly" "jovially" "wholly" "too" "unaccountably" "commonly" "curiously" "softly" "overconfidently" "loftily" "frantically" "dimly" "brightly" "sharply" "noisily" "knowledgeably" "neatly" "wetly" "shakily" "tightly" "recklessly" "thankfully" "gleefully" "suspiciously" "worriedly" "really" "enormously" "gracefully" "blissfully" "jaggedly" "merrily" "willfully" "quicker" "actually" "rigidly" "quizzically" "hungrily" "silently" "kindheartedly" "kookily" "beautifully" "intently" "seriously" "well" "sternly" "supposedly" "usually" "absentmindedly" "bitterly" "briefly" "upward" "repeatedly" "selfishly" "rapidly" "zestily" "roughly" "inwardly" "boldly" "unexpectedly" "only" "valiantly" "usefully" "clearly" "fiercely" "sheepishly" "slowly" "gently" "upright" "blindly" "sedately" "defiantly" "unethically" "kiddingly" "tremendously" "unimpressively" "playfully" "openly" "hourly" "heavily" "unbearably" "justly" "reassuringly" "randomly" "broadly" "unnecessarily" "briskly" "speedily" "yesterday" "annually" "searchingly" "accidentally" "quaintly" "limply" "deceivingly" "poorly" "jubilantly"})

(def places
  #{"saline" "edifice" "hole" "hippodrome" "enclosure" "palisade" "littoral" "odeum" "terminal" "enterprise" "dwelling" "lab" "desk" "range" "foundation" "jail" "river" "crag" "boards" "battleground" "pen" "province" "grassland" "stream" "draught" "castle" "architecture" "parcel" "abode" "mead" "nook" "concern" "levee" "compartment" "coliseum" "shanty" "showroom" "corporation" "park" "sty" "pad" "factory" "emporium" "habitation" "hutch" "top" "courtyard" "diggings" "court" "mansion" "battlefield" "barn" "garrison" "country" "settlement" "playhouse" "beck" "brook" "stead" "lakeshore" "reservation" "lodge" "tide" "rock" "cottage" "belt" "shelter" "pasture" "city" "flood" "company" "homestead" "sector" "height" "margin" "race" "spot" "place" "roof" "part" "area" "drift" "hangar" "reef" "resort" "promontory" "pike" "burn" "coop" "shingle" "five-and-ten" "riverside" "construction" "cot" "booth" "streamlet" "whereabouts" "alkali" "den" "branch" "yard" "villa" "joint" "warehouse" "amphitheater" "brooklet" "framework" "rise" "nest" "deckhouse" "sierra" "elevation" "fabric" "supermarket" "drive-in" "longitude" "dock" "ocean" "orphanage" "eminence" "flat" "compass" "seaside" "puddle" "boutique" "pale" "cell" "camp" "repository" "aviary" "ditch" "drugstore" "locus" "sea" "condominium" "outlet" "run" "acclivity" "hovel" "acreage" "battlefront" "five-and-dime" "post" "beach" "erection" "manor" "base" "block" "condo" "lodging" "box" "rivulet" "altitude" "accommodation" "vinegar" "quarters" "county" "edge" "homeroom" "district" "latitude" "escarpment" "hideout" "deli" "division" "hangout" "shore" "ground" "garden" "cliff" "monument" "boost" "town" "position" "footlights" "lea" "preservative" "flop" "tillage" "business" "office" "flux" "hamlet" "locale" "principality" "rill" "ascent" "section" "movie" "main" "cave" "pond" "runnel" "center" "marinade" "progression" "apartment" "seaboard" "meadow" "ranchland" "suburb" "jet" "harbor" "domicile" "address" "commorancy" "cinema" "lakeside" "theatre" "mesa" "terrain" "bazaar" "cage" "field" "hospital" "study" "region" "towers" "hearth" "stadium" "superstructure" "trailer" "patch" "seabank" "fireside" "setup" "lay" "truck" "workshop" "walk" "tributary" "distance" "township" "venue" "domain" "mountain" "deep" "creek" "site" "berth" "property" "headland" "farmland" "butte" "farm" "habitat" "depot" "territory" "levitation" "glebe" "dump" "port" "dormitory" "chalet" "firm" "locality" "bluff" "community" "corral" "digs" "alp" "skyscraper" "quarter" "backwater" "bodega" "corner" "close" "pigeonhole" "stall" "moorland" "lakefront" "cropland" "square" "riverfront" "cay" "situation" "heave" "rockpile" "precipice" "home" "village" "niche" "shop" "outfit" "deck" "haunt" "drama" "chamber" "rush" "bowl" "state" "palace" "middle" "zone" "dimestore" "quadrangle" "co-op" "vault" "building" "plot" "drink" "estuary" "precinct" "volume" "library" "tract" "exchange" "hut" "strand" "shack" "caboose" "neighborhood" "institute" "pile" "sphere" "kingdom" "cabin" "crib" "peak" "ridge" "realm" "watercourse" "arena" "headquarters" "seashore" "theatrecamp" "room" "juice" "roost" "rindle" "pound" "cubbyhole" "briny" "theater" "mount" "spring" "asylum" "souk" "hall" "stockade" "stretch" "residence" "course" "hill" "streamside" "coast" "crick" "ward" "seafront" "seaway" "prison" "hillock" "station" "brine" "platform" "vicinity" "embankment" "mart" "quad" "ledge" "delicatessen" "mall" "organization" "volcano" "store" "fair" "stand" "sanctuary" "market" "bank" "auditorium" "seat" "point" "dominion" "oceanfront" "casa" "bayou" "house" "plant" "turf" "scene" "dungeon" "spate" "ghetto" "waterfront" "vineyard" "hoist" "shed" "oak" "bungalow"})

(def past-tense-verbs
  #{"Defended" "Awarded" "Responded" "Informed" "Checked" "Verified" "Rendered" "Investigated" "Assured" "Led" "Closed" "Invented" "Computed" "Joined" "Diminished" "Demonstrated" "Interrogated" "Balanced" "Estimated" "Selected" "Changed" "Appraised" "Listened" "Translated" "Brought" "Formulated" "Modeled" "Modified" "Expedited" "Assessed" "Involved" "Conducted" "Earned" "Caused" "Designed" "Reduced" "Cataloged" "Oversaw" "Documented" "Represented" "Coded" "Originated" "Specified" "Excelled" "Convinced" "Utilized" "Counseled" "Synthesized" "Illustrated" "Experienced" "Protected" "Recorded" "Revised" "Concluded" "Consulted" "Analyzed" "Tested" "Participated" "Printed" "Communicated" "Showed" "Corrected" "Conceptualized" "Adapted" "Expanded" "Converted" "Performed" "Instituted" "Used" "Prosecuted" "Improved" "Obtained" "Convened" "Approved" "Delegated" "Handled" "Granted" "Researched" "Simplified" "Planned" "Composed" "Dealt" "Rewrote" "Solved" "Continued" "Articulated" "Cleared" "Shaped" "Drafted" "Managed" "Chose" "Produced" "Wrote" "Engineered" "Highlighted" "Named" "Governed" "Annotated" "Presided" "Executed" "Taught" "Raised" "Accomplished" "Achieved" "Scheduled" "Routed" "Advised" "Explored" "Elicited" "Operated" "Appeared" "Detailed" "Described" "Consolidated" "Targeted" "Presented" "Mediated" "Foresaw" "Adjusted" "Built" "Retrieved" "Encouraged" "Immunized" "Guided" "Expressed" "Clarified" "Identified" "Worked" "Processed" "Edited" "Resolved" "Distributed" "Conveyed" "Formed" "Interviewed" "Incorporated" "Debated" "Generated" "Spoke" "Established" "Extended" "Exhibited" "Publicized" "Examined" "Chartered" "Influenced" "Studied" "Monitored" "Supported" "Started" "Promoted" "Repaired" "Administered" "Provided" "Reviewed" "Controlled" "Justified" "Solicited" "Contacted" "Required" "Perceived" "Classified" "Helped" "Implemented" "Delivered" "Experimented" "Assembled" "Litigated" "Mastered" "Grouped" "Saved" "Judged" "Collected" "Commented" "Assisted" "Combined" "Compared" "Assumed" "Devised" "Organized" "Restored" "Motivated" "Discovered" "Stimulated" "Answered" "Served" "Determined" "Evaluated" "Assigned" "Persuaded" "Aided" "Observed" "Interpreted" "Directed" "Appointed" "Marketed" "Decided" "Explained" "Coordinated" "Acquired" "Shared" "Gained" "Educated" "Defined" "Developed" "Measured" "Licensed" "Compiled" "Budgeted" "Questioned" "Keynoted" "Trained" "Specialized" "Kept" "Moderated" "Summarized" "Constructed" "Calculated" "Completed" "Reproduced" "Briefed" "Gathered" "Received" "Applied" "Reported" "Arbitrated" "Effected" "Retained" "Supervised" "Outlined" "Increased" "Filed" "Ordered" "Argued" "Lobbied" "Collaborated" "Structured" "Activated" "Lectured" "Contracted" "Searched" "Maintained" "Anticipated" "Negotiated" "Correlated" "Tutored" "Corresponded" "Inspected" "Allocated" "Chaired" "Introduced" "Arranged" "Accelerated" "Conceived" "Enlarged"})

(def prepositions #{"within" "underneath" "beside" "under" "towards" "worth" "toward" "after" "off" "among" "beyond" "over" "onto" "about" "without" "for" "past" "along" "outside" "despite" "on" "above" "opposite" "until" "amongst" "besides" "between" "against" "across" "beneath" "with" "around" "through" "next to" "behind" "aside" "before" "below" "near"})

(def greek-alphabet #{"gamma" "eta" "omicron" "nu" "delta" "upsilon" "sigma" "iota" "rho" "omega" "psi" "pi" "kappa" "chi" "zeta" "tau" "mu" "theta" "alpha" "beta" "xi" "lambda" "phi" "epsilon"})

(def regular-verbs #{"skip" "release" "tug" "sound" "drip" "stop" "bow" "dare" "analyse" "scare" "dislike" "trip" "drain" "confuse" "chew" "spell" "compare" "ban" "dam" "pump" "provide" "employ" "dust" "tame" "transport" "clap" "colour" "rub" "whip" "wish" "chase" "soak" "rob" "doubt" "wink" "promise" "save" "curl" "educate" "book" "screw" "treat" "tumble" "compete" "cover" "bare" "punch" "tick" "balance" "disarm" "concern" "rule" "develop" "wonder" "dance" "bomb" "blot" "wipe" "excuse" "suffer" "smash" "beam" "choke" "rejoice" "squeak" "call" "squeeze" "curve" "concentrate" "blind" "brake" "succeed" "thaw" "talk" "explain" "spill" "breathe" "rock" "tease" "copy" "command" "boast" "shelter" "cure" "correct" "race" "spot" "tip" "reply" "whistle" "fade" "separate" "burn" "serve" "sniff" "expand" "dry" "weigh" "spark" "agree" "clip" "record" "exercise" "travel" "bless" "try" "drown" "settle" "alert" "branch" "crack" "calculate" "queue" "trace" "encourage" "embarrass" "decide" "cross" "bake" "boil" "return" "sip" "repeat" "realise" "smell" "cheat" "spare" "surround" "suspend" "care" "complete" "tempt" "cycle" "disappear" "suspect" "camp" "sack" "smile" "slow" "beg" "stir" "retire" "reflect" "snatch" "train" "stuff" "welcome" "sparkle" "whirl" "charge" "cry" "push" "surprise" "tickle" "scribble" "rely" "wobble" "box" "trouble" "scratch" "worry" "bore" "count" "remain" "disagree" "amuse" "touch" "detect" "expect" "dress" "rot" "regret" "battle" "tie" "stare" "allow" "spoil" "sneeze" "signal" "recognise" "shock" "remember" "cheer" "escape" "slip" "reach" "refuse" "drag" "scatter" "challenge" "face" "sin" "rain" "replace" "stroke" "scrape" "cause" "reproduce" "crush" "apologise" "discover" "check" "drop" "destroy" "consider" "support" "taste" "connect" "confess" "coil" "reign" "accept" "trust" "protect" "sail" "shrug" "desert" "scrub" "announce" "switch" "subtract" "coach" "bubble" "supply" "start" "bury" "request" "tow" "blush" "question" "steer" "remove" "rinse" "borrow" "suck" "advise" "admit" "cough" "delight" "behave" "saw" "strap" "belong" "soothe" "stitch" "telephone" "bounce" "shave" "time" "empty" "ski" "scorch" "thank" "remind" "sigh" "trot" "raise" "pull" "disapprove" "tremble" "describe" "sprout" "stamp" "bang" "afford" "relax" "rhyme" "deceive" "continue" "close" "bat" "strip" "brush" "depend" "explode" "delay" "dream" "produce" "snow" "shop" "consist" "radiate" "rush" "tire" "work" "double" "bathe" "squeal" "crawl" "trick" "exist" "shiver" "enjoy" "divide" "complain" "extend" "satisfy" "blink" "collect" "print" "seal" "roll" "bump" "decay" "suppose" "chop" "scream" "scold" "add" "punish" "wave" "stain" "clean" "drum" "carry" "deliver" "report" "reduce" "clear" "repair" "smoke" "slap" "examine" "trap" "reject" "trade" "stay" "stretch" "shade" "suit" "squash" "rescue" "answer" "damage" "search" "tap" "suggest" "whisper" "enter" "sign" "store" "deserve" "annoy" "entertain" "carve" "receive" "end" "whine" "spray" "communicate" "risk" "earn" "program" "test" "excite" "share" "change" "comb" "puncture" "strengthen" "terrify" "bruise" "step" "crash" "tour" "snore" "ruin" "contain" "admire"})

(def nationalities #{"malagasy" "namibian" "persian" "indian" "lithuanian" "salvadorian" "taiwanese" "bolivian" "syrian" "finnish" "zimbabwean" "icelandic" "nepalese" "venezuelan" "honduran" "cameroonian" "vietnamese" "italian" "hungarian" "malaysian" "irishwoman" "austrian" "ukrainian" "tajikistani" "maltese" "guatemalan" "estonian" "bangladeshi" "paraguayan" "american" "croatian" "iranian" "argentine" "kuwaiti" "cuban" "mozambican" "argentinian" "jamaican" "haitian" "albanian" "indonesian" "portuguese" "spanish" "jordanian" "irish" "algerian" "egyptian" "panamanian" "lebanese" "belgian" "cambodian" "kenyan" "libyan" "latvian" "canadian" "japanese" "senegalese" "welshwoman" "sudanese" "thai" "mexican" "danish" "serbian" "slovak" "tunisian" "arabian" "polish" "nigerian" "welsh" "malian" "dominican" "romanian" "turkish" "norwegian" "bulgarian" "german" "ghanaian" "mongolian" "lao" "englishwoman" "afghan" "israeli" "nicaraguan" "chinese" "scottish" "zambian" "frenchwoman" "russian" "dutch" "fijian" "peruvian" "moroccan" "singaporean" "brazilian" "pakistani" "ethiopian" "chilean" "korea" "swedish" "philippine" "iraqi" "australian" "tongan" "english" "batswana" "ecuadorian" "french" "uruguayan" "columbian" "czech" "greek" "swiss"})

(def us-cities #{"Albuquerque" "Tucson" "Houston" "Toledo" "New" "Pittsburgh" "Denver" "Columbus" "Chicago" "Chandler" "Garland" "Washington" "Memphis" "Orleans" "Tacoma" "Fresno" "Honolulu" "Dallas" "Seattle" "Modesto" "Chesapeake" "Orlando" "Oakland" "Boise" "Nashville" "Scottsdale" "Louisville" "Mesa" "Detroit" "Tampa" "Anaheim" "Lubbock" "Glendale" "Madison" "Paradise" "Aurora" "Anchorage" "Winston-Salem" "Irvine" "Phoenix" "McKinney" "Spokane" "Fremont" "Durham" "Hialeah" "Reno" "Richmond" "Baltimore" "Port" "Stockton" "Boston" "Miami" "Cincinnati" "Lincoln" "Jacksonville" "Irving" "York" "Atlanta" "Milwaukee" "Tulsa" "Cleveland" "Portland" "Henderson" "Omaha" "Laredo" "Bakersfield" "Fontana" "Norfolk" "Gilbert" "City" "Frisco" "Lexington" "Plano" "Riverside" "Indianapolis" "Saint" "Raleigh" "Lucie" "Fayetteville" "Arlington" "Austin" "Philadelphia" "Charlotte" "Buffalo" "Greensboro" "Wichita" "Sacramento" "Minneapolis"})

(def *random-category-map {:nato-alphabet nato-alphabet :animals animals, :numbers numbers, :numbers-in-10s numbers-in-10s, :colors colors, :ing-verbs ing-verbs, :female-names female-first-names, :male-names male-first-names, :nations nations :last-names last-names :zero-to-one-hundred (set (map str (range 0 101))) :adverbs adverbs :places places :past-tense-verbs past-tense-verbs :prepositions prepositions :greek-alphabet greek-alphabet :regular-verbs regular-verbs :nationalities nationalities :us-cities us-cities})

(def random-category-map
  (medley.core/map-vals
   (fn [the-set]
     (into #{} (map (fn [the-string]
                      (-> the-string clojure.string/lower-case)) the-set)))
   *random-category-map))

(def random-categories (set (keys random-category-map)))

;; This is the perfect time to use the coerce system.
(defn rand-of
  ([] (rand-of (rand-nth (vec random-categories))))
  ([k]
   (let [category (get random-category-map k)]
     (when category
       (rand-nth (vec category))))))

(def counts-map (medley.core/map-kv (fn [k v]
                                      [k (count v)]) *random-category-map))

(defonce categories-by-count (apply (partial sorted-map-by <) (flatten (seq (clojure.set/map-invert counts-map)))))

(def categories-by-least (vals (apply (partial sorted-map-by <) (flatten (seq (clojure.set/map-invert counts-map))))))

(defn-spec rand-str string?
  "Creates a random string of `64` or supplied length upper and lower chars + numbers."
  ([] (rand-str 64))
  ([len integer?]
   (let [u (take len (repeatedly #(char (+ (rand 26) 65))))
         d (take len (repeatedly #(clojure.string/lower-case (char (+ (rand 26) 65)))))]
     (apply str (take len (shuffle (flatten [u d])))))))

(defn-spec qid string?
  "Utility to create a simply id that's a unique string. Placeholder."
  []
  (rand-str 64))

(comment
  (require '[clojure.spec.alpha :as s])

  (gen/generate (s/gen int?))
  (gen/generate (s/gen (s/and int? #(< % 10000) #(> % 50))))

  (gen/generate (s/gen ::ttt))

  (gen/sample (s/gen #{:club :diamond :heart :spade}))
  (gen/sample (s/gen (s/cat :k keyword? :ns (s/+ number?))))

  (gen/sample (s/gen nato-alphabet))
  (gen/sample (s/gen (s/cat :k nato-alphabet :v greek-alphabet)))
  (defn divisible-by [n] #(zero? (mod % n)))

  (gen/sample (s/gen (s/and int?
                            (s/and #(> % 0) #(> 10000 %))
                            (divisible-by 3))))
  (defn gen-map-kv
    ([k-spec v-spec] (gen-map-kv k-spec v-spec 10))
    ([k-spec v-spec n]
     (let [raw (s/exercise (s/cat :k k-spec :v v-spec) n)
           m (apply merge (map (comp #(apply hash-map %) first) raw))]
       m)))

  (gen-map-kv nato-alphabet greek-alphabet)

  (s/exercise (s/cat :k nato-alphabet :v greek-alphabet))
  (s (s/cat :k nato-alphabet :v greek-alphabet) 10)

  (defn gen-qkw-in-ns [nmsp example-set]
    (s/with-gen (s/and keyword? #(= (namespace %) nmsp))
      #(s/gen example-set)))

  (s/def :ex/p (gen-qkw-in-ns "patty" #{:patty/time :patty/love :patt/play}))
  (gen/sample (s/gen :ex/p) 100)
  (s/def :ex/kws)

  (s/valid? :ex/p :patty/fresh)
  (gen/sample (s/gen :ex/kws))

  (def kw-gen-2 (gen/fmap #(keyword "my.domain" %)
                          (gen/string-alphanumeric)))
  (def kw-gen-3 (gen/fmap #(keyword "my.domain" %)
                          (gen/such-that #(and (< 3 (count %)) (not= % "")) (gen/string-alphanumeric))))

  (s/def ::ttt keyword?)

  (s/def :ex/gb (s/with-gen
                  #(s/valid? ::ttt %)
                  (gen/fmap #(keyword "my.domain" %)
                            (gen/such-that #(and (< 3 (count %)) (not= % "")) (gen/string-alphanumeric)))))

  (s/exercise (gen/sample (s/with-gen
                            #(s/valid? ::ttt %)
                            (gen/fmap #(keyword "my.domain" %)
                                      (gen/such-that #(and (< 3 (count %)) (not= % "")) (gen/string-alphanumeric))))))

  (s/def :ex/hello
    (s/with-gen #(clojure.string/includes? % "hello")
      #(gen/fmap (fn [[s1 s2]] (str s1 "hello" s2))
                 (gen/tuple (gen/string-alphanumeric) (gen/string-alphanumeric)))))

  (s/def :ex/hello
    (s/with-gen #(clojure.string/includes? % "hello")
      #(gen/fmap (fn [[s1 s2]] (str s1 "hello" s2))
                 (gen/tuple (gen/double) (gen/boolean)))))

  (defn g [sp]
    (gen/generate (s/gen sp)))

  (defn s
    ([sp]
     (first (drop 50 (gen/sample (s/gen sp) 51))))
    ([sp n]
     (vec (drop 50 (gen/sample (s/gen sp) (+ 50 n))))))

  (defn fmapped-generator [pred fmap-fn genny]
    (s/with-gen pred
      #(gen/fmap fmap-fn genny)))

  (s (fmapped-generator #(clojure.string/starts-with? % "sexy")
                        (fn [t] (str "sexy_" t))
                        (gen/string-alphanumeric)))
  (require '[clojure.test.check.generators :as test.g])

  (defn abs [^long v] (Math/abs v))

  (-> int?
      (s/with-gen #(gen/fmap abs (s/gen int?)))
      s/gen
      gen/generate)

  (def gen-rand-digit (gen/elements (range 10)))

  (defn int-in-range [s e]
    (s/with-gen
      (s/int-in s e)
      (constantly (gen/elements (range s e)))))

  (s/def :ex/thirties (int-in-range 30 40))
  (s/def :ex/i-or-nil (s/or ::int int? ::nil nil?))
  (gen/generate (s/gen :ex/i-or-nil))
  (defn gen-spec [s] (gen/generate (s/gen s)))

  (s/def ::small-num (s/with-gen
                       (s/int-in 0 10)
                       (constantly gen-rand-digit)))

  (defn dynamic-generator [g]
    (if (string? g)
      (get {"alphanumeric-string" (gen/string-alphanumeric)
            "any" (gen/any)
            "?" (gen/boolean)
            "str" (gen/string)
            "double" (gen/double)
            "kw" (gen/keyword)} g)
      g))

  (def zip-code
    (->> (gen/tuple (gen/choose 100 999)
                    (gen/choose 10 99))
         (gen/fmap
          (fn [[x y]] (str x y)))))


  
  (defn generate
    ([generator]
     (if (string? generator)
       (gen/generate (dynamic-generator generator))
       (gen/generate generator)))
    ([pred generator]
     (gen/generate (s/gen (s/with-gen pred (fn [] (dynamic-generator generator)))))))

  (generate (gen/double))
  (generate "double")
  (generate double? (gen/double))
  (generate double? "double")
  (generate)
  (generate integer?
            (gen/fmap identity (gen/int)))
  (generate integer? (gen/fmap identity (gen/int)))
  (generate integer? (gen/int))

  (type (gen/fmap identity (gen/int)))

  (s/def :ex/gass (generator-from-generator #(clojure.string/includes? % "hello")
                                            (fn [[s1 s2]] (str s1 "hello" s2))
                                            (gen/tuple (gen/double) (gen/boolean))))

  (s/exercise)
  (generate-from-generator :ex/gass)
  (generate-from-generator #(clojure.string/includes? % "hello")
                           (fn [[s1 s2]] (str s1 "hello" s2))
                           (gen/tuple (gen/double) (gen/boolean)))

  (gen/sample (s/gen (generator-from-generator #(clojure.string/includes? % "hello")
                                               (fn [[s1 s2]] (str s1 "hello" s2))
                                               (gen/tuple (gen/double) (gen/boolean)))))

  (defn vt-example
    ([vt]
     (drop 50 (gen/sample (s/gen vt) 51)))
    ([vt n]
     (let [n (+ n 50)]
       (drop 50 (gen/sample (s/gen vt) n)))))

  (s/def :ex/tens (s/int-in 10 20))
  (s/def :ex/twenties (s/int-in 20 30))
  (gen/sample (s/gen :ex/tens))
  (s :ex/tens)
  (s :ex/twenties 10)

  (s/def :bowling/roll (s/int-in 0 11))
  (gen/sample (s/gen :bowling/roll))
  (s/def :ex/the-aughts (s/inst-in #inst "2000" #inst "2010"))
  (drop 50 (gen/sample (s/gen :ex/the-aughts) 55))

  (s/def :ex/dubs (s/double-in :min -100.0 :max 100.0 :NaN? false :infinite? false))

  (s/valid? :ex/dubs 2.9)
  (gen/sample (s/gen :ex/dubs))
  (gen/sample (gen/set (gen/int)))
  (gen/sample kw-gen-2 5)
  (gen/sample kw-gen-3 5)

  #_(defn new-unordered-queue-id [] (str "ulysses-" (rand-of :ing-verbs) "-" (rand-of :female-names)))
  #_(defn new-ordered-queue-id [] (str "ophelia-" (rand-of :ing-verbs) "-" (rand-of :male-names))))

#_(rand-of :animals)
#_(defn-spec replace-token-in-map :vt/map [token :vt/qkw replacement :vt/any m :vt/map]
    (clojure.walk/postwalk #(if (= token %) replacement %) m))
#_(str (rand-of :female-names) " " (rand-of :last-names) " " (rand-of :ing-verbs) " in " (rand-of :nations))

#_(defn-spec rando :vt/qkw-or-nil
    ([thing :vt/any] (rando :RANDO/RANDO thing))
    ([variant :vt/qkw thing :vt/any]
     (let [random-token])
     (cond
       (map? thing) (replace-token-in-map :RANDO/RANDO :HAPPY thing)
       (vector? thing) (id-kw-for-path thing)
       (qualified-keyword? thing) (keyword (namespace thing) "id")
       (keyword? thing) (keyword (name thing) "id")
       (string? thing) (keyword thing "id")
       :else nil)))
