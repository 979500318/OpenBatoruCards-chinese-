package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_CodeHeartAUtomassager extends Card {
    
    public SIGNI_K3_CodeHeartAUtomassager()
    {
        setImageSets("WXDi-P07-048");
        
        setOriginalName("コードハート　Aトマッサージャー");
        setAltNames("コードハートエートマッサージャー Koodo Haato Ee To Massaajaa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたがスペルを１枚以上使用していた場合、対戦相手のデッキの上からカードを２枚トラッシュに置く。その後、このターンにあなたがスペルを２枚以上使用していた場合、あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E %K：あなたのトラッシュから黒のスペル１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "A - To Massager, Code: Heart");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you have used one or more spells this turn, put the top two cards of your opponent's deck into their trash. Then, if you used two or more spells this turn, add target black SIGNI from your trash to your hand.\n" +
                "@E %K: Add target black spell from your trash to your hand."
        );
        
        setName("en_fan", "Code Heart A Utomassager");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if you used 1 or more spells this turn, put the top 2 cards of your opponent's deck into the trash. Then, if you used 2 or more spells this turn, target 1 black SIGNI from your trash, and add it to your hand.\n" +
                "@E %K: Target 1 black spell from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "爱心代号 按摩椅");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把魔法1张以上使用过的场合，从对战对手的牌组上面把2张牌放置到废弃区。然后，这个回合你把魔法2张以上使用过的场合，从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n" +
                "@E %K:从你的废弃区把黑色的魔法1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            long countUsedSpells = GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller()));
            if(countUsedSpells >= 1)
            {
                millDeck(getOpponent(), 2);
                
                if(countUsedSpells >= 2)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
                    addToHand(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
    }
}
