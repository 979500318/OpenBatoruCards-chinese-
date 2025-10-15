package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

public final class SIGNI_RWB1_AArrowHeavenlyAngel extends Card {

    public SIGNI_RWB1_AArrowHeavenlyAngel()
    {
        setImageSets("WXDi-P16-088");

        setOriginalName("混天　Ａ・アロー");
        setAltNames("コンテンエーアロー Konten Ee Aroo");
        setDescription("jp",
                "=T ＜夢限少女＞\n" +
                "^U $T1：あなたのルリグ１体がアタックしたとき、対戦相手が%Xを支払うか手札を１枚捨てるか自分のシグニ１体を場からトラッシュに置かないかぎり、対戦相手のライフクロス１枚をクラッシュする。\n\n" +
                "@C：あなたの場に＜夢限少女＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "A - Arrow, Chaos Angel");
        setDescription("en",
                "=T <<MUGEN SHOJO>> \n^U $T1: When a LRIG on your field attacks, crush one of your opponent's Life Cloth unless they pay %X, discard a card, or put a SIGNI on their field into its owner's trash.\n\n@C: This card loses its colors in all zones unless there are three <<MUGEN SHOJO>> LRIG on your field."
        );
        
        setName("en_fan", "A Arrow, Heavenly Angel");
        setDescription("en_fan",
                "=T <<Mugen Shoujo>>\n" +
                "^U $T1: When your LRIG attacks, crush 1 of your opponent's life cloth, unless they pay %X or discard 1 card from their hand or put 1 of their SIGNI from the field into the trash.\n\n" +
                "@C: If there aren't 3 <<Mugen Shoujo>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "混天 A·矢箭");
        setDescription("zh_simplified", 
                "=T<<夢限少女>>（假如你的场上的<<夢限少女>>的分身在3只，那么^U能力有效）\n" +
                "^U$T1 当你的分身1只攻击时，如果对战对手不把%X:支付或手牌1张舍弃或从场上把自己的精灵1只放置到废弃区，那么对战对手的生命护甲1张击溃。\n" +
                "@C :你的场上的<<夢限少女>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO) && isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!pay(getOpponent(), new EnerCost(Cost.colorless(1)), new DiscardCost(0,1), new TrashCost(0,1, new TargetFilter().SIGNI())))
            {
                crush(getOpponent());
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}
