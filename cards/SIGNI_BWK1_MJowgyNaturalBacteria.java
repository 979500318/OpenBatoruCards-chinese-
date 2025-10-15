package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

public final class SIGNI_BWK1_MJowgyNaturalBacteria extends Card {

    public SIGNI_BWK1_MJowgyNaturalBacteria()
    {
        setImageSets("WXDi-P16-091");

        setOriginalName("羅菌　Ｍ・ジョーギ");
        setAltNames("ラキンエムジョーギ Rakin Emu Joogi M-Jowgy");
        setDescription("jp",
                "=T ＜きゅるきゅるーん☆＞\n" +
                "^U：あなたのアタックフェイズ開始時、対戦相手は%Xを支払わないかぎり、手札を１枚捨てる。\n\n" +
                "@C：あなたの場に＜きゅるきゅるーん☆＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "M - Ruler, Natural Bacteria");
        setDescription("en",
                "=T <<Kyurukyurun\u2606>>\n^U: At the beginning of your attack phase, your opponent discards a card unless they pay %X.\n\n@C: This card loses its colors in all zones unless there are three <<Kyurukyurun\u2606>> LRIG on your field."
        );
        
        setName("en_fan", "M Jowgy, Natural Bacteria");
        setDescription("en_fan",
                "=T <<Kyurukyurun☆>>\n" +
                "^U: At the beginning of your attack phase, your opponent discards 1 card from their hand unless they pay %X.\n\n" +
                "@C: If there aren't 3 <<Kyurukyurun☆>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "罗菌 M·乔吉");
        setDescription("zh_simplified", 
                "=T<<きゅるきゅるーん☆>>\n" +
                "^U你的攻击阶段开始时，对战对手如果不把%X:支付，那么把手牌1张舍弃。\n" +
                "@C :你的场上的<<きゅるきゅるーん☆>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }

        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.KYURUKYURUN) &&
                   isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!payEner(getOpponent(), Cost.colorless(1)))
            {
                discard(getOpponent(), 1);
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.KYURUKYURUN) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}
