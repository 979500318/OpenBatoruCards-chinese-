package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_UNKNOWNCODERU extends Card {

    public SIGNI_W3_UNKNOWNCODERU()
    {
        setImageSets("WXDi-P13-004B");

        setOriginalName("ＵＮＫＮＯＷＮ－ＣＯＤＥ－ＲＵ－");
        setAltNames("アンノウンコードルウ Announ Koodo Ruu");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニは【シャドウ】を得る。\n" +
                "@U：このシグニがアタックしたとき、カードを１枚引くか【エナチャージ１】をする。\n" +
                "@E：次のあなたのエナフェイズ終了時まで、このシグニが場にあるかぎり、あなたのセンタールリグのリミットを＋２する。"
        );

        setName("en", "UNKNOWN - CODE - RU -");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow]]. \n@U: Whenever this SIGNI attacks, draw a card or [[Ener Charge 1]].\n@E: Until the end of your next Ener Phase, as long as this SIGNI remains on the field, increase your Center LRIG's limit by two."
        );
        
        setName("en_fan", "UNKNOWN-CODE-RU-");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow]].\n" +
                "@U: Whenever this SIGNI attacks, draw 1 card or [[Ener Charge 1]].\n" +
                "@E: Until the end of your next ener phase, as long as this SIGNI is on your field, your center LRIG gets +2 limit."
        );

		setName("zh_simplified", "UNKNOWN-CODE-RU-");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵得到[[暗影]]。（这只精灵不会被对战对手作为对象）\n" +
                "@U :当这只精灵攻击时，抽1张牌或[[能量填充1]]。\n" +
                "@E :直到下一个你的充能阶段结束时为止，这只精灵在场上时，你的核心分身的界限+2。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ORIGIN);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private void onAutoEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
        
        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().LRIG(), new ModifiableValueModifier<>(this::onAttachedConstEffModGetSample, () -> 2d));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getCardIndex().isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLimit();
        }
    }
}
