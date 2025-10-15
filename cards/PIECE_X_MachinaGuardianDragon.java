package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_MachinaGuardianDragon extends Card {
    
    public PIECE_X_MachinaGuardianDragon()
    {
        setImageSets("WXDi-P05-003");
        
        setOriginalName("M.G.D.");
        setAltNames("マキナガーディアンドラゴン M. G. D. MGD");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のルリグ１体を対象とし、「あなたのエナゾーンからカード１枚をトラッシュに置く。」か「手札を１枚捨てる。」を合計３回行う。そうした場合、このターン、それがアタックしたとき、そのアタックを無効にする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Machina Guardian Dragon");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Perform the following action \"Put a card from your Ener Zone into your trash.\" or \"Discard a card.\" a total of three times. If you do, whenever target LRIG on your opponent's field attacks this turn, negate that attack.\n" +
                "$$2 Draw a card."
        );
        
        setName("en_fan", "Machina Guardian Dragon");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIGs, and do \"Put 1 card from your ener zone into the trash.\" and/or \"Discard 1 card from your hand.\" 3 times. If you do, this turn, whenever it attacks, disable that attack.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "M.G.D.");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的分身1只作为对象，\n" +
                "@>:从你的能量区把1张牌放置到废弃区。@@\n" +
                "@>:手牌1张舍弃。@@\n" +
                "合计进行3次。这样做的场合，这个回合，当其攻击时，那次攻击无效。\n" +
                "$$2 抽1张牌。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            piece.setModeChoice(1);
        }
        
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter().OP().LRIG()).get();
                
                int countResolved = 0;
                for(int i=0;i<3;i++)
                {
                    if(playerChoiceAction(ActionHint.TRASH, ActionHint.DISCARD) == 1)
                    {
                        CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                        if(trash(cardIndex)) countResolved++;
                    } else {
                        if(discard(1).get() != null) countResolved++;
                    }
                }
                
                if(countResolved == 3)
                {
                    addCardRuleCheck(CardRuleCheckType.CAN_LAND_ATTACK, target, ChronoDuration.turnEnd(), data -> {
                        return RuleCheckState.BLOCK;
                    });
                }
            } else {
                draw(1);
            }
        }
    }
}
