package open.batoru.data.cards;

import open.batoru.catalog.description.DescriptionParser;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W2_AstolfoHolyGeneral extends Card {

    public SIGNI_W2_AstolfoHolyGeneral()
    {
        setImageSets("WXDi-P11-054");

        setOriginalName("聖将　アストルフォ");
        setAltNames("セイショウアストルフォ Seishou Asutorufo");
        setDescription("jp",
                "@C：あなたの場にあるすべてのシグニが白であるかぎり、このシグニのパワーは＋4000される。\n" +
                "@C：あなたのトラッシュに白のカードが１５枚以上あるかぎり、このシグニは@>@U：対戦相手のターンの間、[[シャドウ（{{このシグニのパワー以下のシグニ$パワー%1以下のシグニ}}）]]を得る。@@を得る。"
        );

        setName("en", "Astolfo, Blessed General");
        setDescription("en",
                "@C: As long as all of the SIGNI on your field are white, this SIGNI gets +4000 power.\n" +
                "@C: As long as there are fifteen or more white cards in your trash, this SIGNI gains@>@C: During your opponent's turn, this SIGNI gains [[Shadow -- {{SIGNI with power less than or equal to this SIGNI$SIGNI with power %1 or less}}]].@@"
        );
        
        setName("en_fan", "Astolfo, Holy General");
        setDescription("en_fan",
                "@C: As long as all of your SIGNI are white, this SIGNI gets +4000 power.\n" +
                "@C: As long as there are 15 or more white cards in your trash, this SIGNI gains:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow ({{SIGNI with power equal to or less than this SIGNI's$SIGNI with power %1 or less}})]]."
        );

		setName("zh_simplified", "圣将 阿斯托尔福");
        setDescription("zh_simplified", 
                "@C :你的场上的全部的精灵是白色时，这只精灵的力量+4000。\n" +
                "@C :你的废弃区的白色的牌在15张以上时，这只精灵得到\n" +
                "@>@C :对战对手的回合期间，得到[[暗影（这只精灵的力量以下的精灵）]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(4000));
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
        }
        
        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().SIGNI().not(new TargetFilter().withColor(CardColor.WHITE)).getValidTargetsCount() == 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return new TargetFilter().own().withColor(CardColor.WHITE).fromTrash().getValidTargetsCount() >= 15 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(this::onAttachedConstEffCond, new AbilityGainModifier(this::onAttachedConstEffModGetSample));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond, () -> DescriptionParser.formatNumber(getPower().getValue().intValue())));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getPower().getValue() <= getPower().getValue() ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
