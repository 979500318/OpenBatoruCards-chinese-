package open.batoru.data.cards;

import open.batoru.catalog.description.DescriptionParser;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

import java.util.List;
import java.util.StringJoiner;

public final class SIGNI_X3_SpinnerMemoria extends Card {

    public SIGNI_X3_SpinnerMemoria()
    {
        setImageSets("WXDi-P16-058", "WXDi-P16-058P");

        setOriginalName("紡ぐ者//メモリア");
        setAltNames("ツムグモノメモリア Tsumugumono Memoria");
        setDescription("jp",
                "@C：このシグニはあなたの場にいるルリグが持つ色を得る。\n" +
                "@C：対戦相手のターンの間、[[シャドウ（{{このシグニが持つ色$%1}}）]]を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニと共通する色を持たない対戦相手のシグニ１体を対象とし、%X %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Spinner of Stories//Memoria");
        setDescription("en",
                "@C: This SIGNI gains the colors of LRIG on your field.\n@C: During your opponent's turn, this SIGNI gains [[Shadow -- {{This SIGNI's colors$%1}}]]. \n@U: At the beginning of your attack phase, you may pay %X %X. If you do, vanish target SIGNI on your opponent's field that does not share a color with this SIGNI."
        );
        
        setName("en_fan", "Spinner//Memoria");
        setDescription("en_fan",
                "@C: This SIGNI gains the colors of all of your LRIG on the field.\n" +
                "@C: During your opponent's turn, this SIGNI gains [[Shadow ({{this SIGNI's colors$%1}})]].\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI that doesn't share a common color with this SIGNI, and you may pay %X %X. If you do, banish it."
        );

		setName("zh_simplified", "纺织者//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵得到你的场上的分身的持有颜色。\n" +
                "@C :对战对手的回合期间，得到[[暗影（这只精灵的持有颜色）]]。（这只精灵不会被对战对手的，对应颜色的能力和效果作为对象）\n" +
                "@U :你的攻击阶段开始时，不持有与这只精灵共通颜色的对战对手的精灵1只作为对象，可以支付%X %X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ORIGIN);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new ModifiableBaseValueModifier<>(this::onConstEff1ModGetSample, this::onConstEff1ModGetValue));
            
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private CardDataColor onConstEff1ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
        private List<CardColor> onConstEff1ModGetValue(List<CardColor> oldBaseValue)
        {
            return getLRIGs(getOwner()).stream().flatMap(cardIndex -> cardIndex.getIndexedInstance().getColor().getValue().stream()).distinct().toList();
        }
        
        private ConditionState onConstEff2Cond()
        {
            return !isOwnTurn() && getColor().getPrimaryValue() != CardColor.COLORLESS ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond, this::onAttachedStockEffDynamicDesc));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getColor().matches(getColor()) ? ConditionState.OK : ConditionState.BAD;
        }
        private String onAttachedStockEffDynamicDesc()
        {
            StringJoiner result = new StringJoiner(DescriptionParser.formatSeparator());
            getColor().getValue().forEach(color -> result.add(color.getLabel()));
            return result.toString();
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().not(new TargetFilter().withColor(getColor()))).get();

            if(target != null && payEner(Cost.colorless(2)))
            {
                banish(target);
            }
        }
    }
}

