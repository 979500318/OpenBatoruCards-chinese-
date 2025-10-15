package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G3_ZeruelVerdantAngel extends Card {
    
    public SIGNI_G3_ZeruelVerdantAngel()
    {
        setImageSets("WXDi-P04-077");
        
        setOriginalName("翠天　ゼルエル");
        setAltNames("スイテンゼルエル Suiten Zerueru");
        setDescription("jp",
                "@E %W %R %B %G %K：対戦相手のシグニを２体まで対象とし、それらをエナゾーンに置く。"
        );
        
        setName("en", "Zeruel, Jade Angel");
        setDescription("en",
                "@E %W %R %B %G %K: Put up to two target SIGNI on your opponent's field into its owner's Ener Zone."
        );
        
        setName("en_fan", "Zeruel, Verdant Angel");
        setDescription("en_fan",
                "@E %W %R %B %G %K: Target up to 2 of your opponent's SIGNI, and put them into the ener zone."
        );
        
		setName("zh_simplified", "翠天 塞路尔");
        setDescription("zh_simplified", 
                "@E %W%R%B%G%K:对战对手的精灵2只最多作为对象，将这些放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.color(CardColor.RED, 1) + Cost.color(CardColor.BLUE, 1) +
                                               Cost.color(CardColor.GREEN, 1) + Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).OP().SIGNI());
            putInEner(data);
        }
    }
}
