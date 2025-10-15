package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public class SIGNI_K1_MikazukiDissonaNaturalStar extends Card {

    public SIGNI_K1_MikazukiDissonaNaturalStar()
    {
        setImageSets("WXDi-P13-084");

        setOriginalName("羅星　ミカヅキ//ディソナ");
        setAltNames("ラセイミカヅキディソナ Rasei Mikazuki Disona");
        setDescription("jp",
                "@E %X %X：あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュから#Sのシグニ１枚を対象とし、それを手札に加える。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Mikazuki//Dissona, Natural Planet");
        setDescription("en",
                "@E %X %X: Put the top two cards of your deck into your trash. Then, add target #S SIGNI from your trash to your hand." +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Mikazuki//Dissona, Natural Star");
        setDescription("en_fan",
                "@E %X %X: Put the top 2 cards of your deck into the trash. Then, target up to 1 #S @[Dissona]@ SIGNI from your trash, and add it to your hand." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "罗星 银钩//失调");
        setDescription("zh_simplified", 
                "@E %X %X从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把#S的精灵1张作为对象，将其加入手牌。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            millDeck(2);

            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().dissona().fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
