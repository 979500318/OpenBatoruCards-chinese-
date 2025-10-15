package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_RB_ITTENTOPPA extends Card {
    
    public PIECE_RB_ITTENTOPPA()
    {
        setImageSets("WXDi-P02-002");
        
        setOriginalName("ITTEN-TOPPA");
        setAltNames("イッテントッパ Itten Toppa");
        setDescription("jp",
                "=U あなたの場に赤と青のルリグがいる\n\n" +
                "@[@|以下の４つから２つまで選ぶ。|@]@\n" +
                "$$1対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "$$3対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "$$4カードを１枚引く。"
        );
        
        setName("en", "ITTEN - TOPPA");
        setDescription("en",
                "=U You have a red LRIG and a blue LRIG on your field.\n\n" +
                "Choose up to two of the following.\n" +
                "$$1 Vanish target SIGNI on your opponent's field with power 8000 or less.\n" +
                "$$2 Your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "$$3 Your opponent discards a card at random.\n" +
                "$$4 Draw a card."
        );
        
        setName("en_fan", "ITTEN-TOPPA");
        setDescription("en_fan",
                "=U There is a red and a blue LRIG on your field\n\n" +
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "$$2 Your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "$$3 Choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "$$4 Draw 1 card."
        );
        
		setName("zh_simplified", "ITTEN-TOPPA");
        setDescription("zh_simplified", 
                "=U你的场上有红色和蓝色的分身\n" +
                "从以下的4种选2种最多。\n" +
                "$$1 对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "$$3 不看对战对手的手牌选1张，舍弃。\n" +
                "$$4 抽1张牌。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.RED, CardColor.BLUE);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(0,2);
        }
        
        private ConditionState onPieceEffCond()
        {
            return (new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0 &&
                    new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & (1<<0)) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                banish(target);
            }
            if((modes & (1<<1)) != 0)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
            if((modes & (1<<2)) != 0)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
            if((modes & (1<<3)) != 0)
            {
                draw(1);
            }
        }
    }
}
