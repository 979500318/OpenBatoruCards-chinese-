package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class PIECE_X_TuningUp extends Card {

    public PIECE_X_TuningUp()
    {
        setImageSets("WXDi-P14-070");

        setOriginalName("ちゅーにんぐあっぷ！");
        setAltNames("チューニングアップ Chuuningu Appu");
        setDescription("jp",
                "あなたのトラッシュから＜電音部＞のシグニを３枚まで対象とし、それらを場に出す。あなたのセンタールリグがレベル３以上の場合、次の対戦相手のターンの間、あなたの＜電音部＞のシグニは【シャドウ】を得る。"
        );

        setName("en", "Tuning Up!");
        setDescription("en",
                "Put up to three target <<DEN-ON-BU>> SIGNI from your trash onto your field. If your Center LRIG is level three or more, during your opponent's next turn, <<DEN-ON-BU>> SIGNI on your field gain [[Shadow]]. "
        );
        
        setName("en_fan", "Tuning Up!");
        setDescription("en_fan",
                "Target up to 3 <<Denonbu>> SIGNI from your trash, and put them onto the field. If you center LRIG is level 3 or higher, during your opponent's next turn, all of your <<Denonbu>> SIGNI gain [[Shadow]]."
        );

		setName("zh_simplified", "啾音相印！");
        setDescription("zh_simplified", 
                "从你的废弃区把<<電音部>>精灵3张最多作为对象，将这些出场。你的核心分身在等级3以上的场合，下一个对战对手的回合期间，你的<<電音部>>精灵得到[[暗影]]。\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(3));
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

            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
        }
        
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromTrash().playable()));
        }
        private void onPieceEff()
        {
            putOnField(piece.getTargets());
            
            if(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU),
                    new AbilityGainModifier(this::onAttachedConstEffModGetSample)
                );
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
    }
}
