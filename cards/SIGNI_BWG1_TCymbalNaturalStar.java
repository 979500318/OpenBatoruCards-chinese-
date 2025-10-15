package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

import java.util.Collections;

public final class SIGNI_BWG1_TCymbalNaturalStar extends Card {

    public SIGNI_BWG1_TCymbalNaturalStar()
    {
        setImageSets("WXDi-P16-090");

        setOriginalName("羅星　Ｔ・シンバル");
        setAltNames("ラセイティーシンバル Rasei Tii Shinbaru T-Cymbal");
        setDescription("jp",
                "=T ＜うちゅうのはじまり＞\n" +
                "^C：このシグニのパワーはあなたの場にいるルリグのレベルの合計１につき＋1000される。あなたの場にいるルリグのレベルの合計が７であるかぎり、このシグニは[[シャドウ（レベル２以下のシグニ）]]を得る。\n\n" +
                "@C：あなたの場に＜うちゅうのはじまり＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "T - Cymbal, Natural Planet");
        setDescription("en",
                "=T <<UCHU NO HAJIMARI>>\n^C: This SIGNI gets +1000 power for every level of each of LRIG on your field. As long as the total level of LRIG on your field is exactly seven, this SIGNI gains [[Shadow -- Level two or less SIGNI]].\n\n@C: This card loses its colors in all zones unless there are three <<UCHU NO HAJIMARI>> LRIG on your field."
        );
        
        setName("en_fan", "T Cymbal, Natural Star");
        setDescription("en_fan",
                "=T <<Universe's Beginning>>\n" +
                "^C: This SIGNI gets +1000 power for each level of your LRIG on the field. As long as the total level of your LRIG on the field is 7, this SIGNI gains [[Shadow (level 2 or lower SIGNI)]].\n\n" +
                "@C: If there aren't 3 <<Universe's Beginning>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "罗星 T·钹");
        setDescription("zh_simplified", 
                "=T<<うちゅうのはじまり>>\n" +
                "^C:这只精灵的力量依据你的场上的分身的等级的合计的数量，每有1级就+1000。你的场上的分身的等级的合计在7时，这只精灵得到[[暗影（等级2以下的精灵）]]。\n" +
                "@C :你的场上的<<うちゅうのはじまり>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.WHITE, CardColor.GREEN);
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

            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(this::onConstEff1Mod1GetValue), new AbilityGainModifier(this::onConstEff1Mod2Cond, this::onConstEff1Mod2GetSample));

            ConstantAbility cont = registerConstantAbility(this::onConstEff2Cond, new ModifiableBaseValueModifier<>(this::onConstEff2ModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }

        private ConditionState onConstEff1Cond()
        {
            return isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING) ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEff1Mod1GetValue(CardIndex cardIndex)
        {
            return 1000 * new TargetFilter().own().anyLRIG().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum();
        }
        private ConditionState onConstEff1Mod2Cond()
        {
            return new TargetFilter().own().anyLRIG().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() == 7 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff1Mod2GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return !isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}
