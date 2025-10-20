package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.ExceedCost;

public final class SIGNI_B2_JackFrostAzureDevil extends Card {
    
    public SIGNI_B2_JackFrostAzureDevil()
    {
        setImageSets("WXDi-P06-063");
        
        setOriginalName("蒼魔　ジャックフロスト");
        setAltNames("ソウマジャックフロスト Souma Jakku Furosuto");
        setDescription("jp",
                "@E @[エクシード３]@：対戦相手のすべてのシグニを凍結する。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Jack Frost, Azure Evil");
        setDescription("en",
                "@E @[Exceed 3]@: Freeze all SIGNI on your opponent's field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Jack Frost, Azure Devil");
        setDescription("en_fan",
                "@E @[Exceed 3]@: Freeze all of your opponent's SIGNI." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "苍魔 杰克冻人");
        setDescription("zh_simplified", 
                "@E @[超越 3]@（从你的分身的下面把牌合计3张放置到分身废弃区）:对战对手的全部的精灵冻结。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new ExceedCost(3), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            freeze(getSIGNIOnField(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
