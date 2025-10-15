package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G3_WolfriendDissonaPhantomBeastDeity extends Card {

    public SIGNI_G3_WolfriendDissonaPhantomBeastDeity()
    {
        setImageSets("WXDi-P13-052");
        setLinkedImageSets("WXDi-P13-009");

        setOriginalName("幻獣神　ウルフレンド//ディソナ");
        setAltNames("ゲンジュウシンウルフレンドディソナ Genjuushin Urufurendo Disona");
        setDescription("jp",
                "@C：このシグニはパワーが30000以上であるかぎり、@>@C：このシグニは対戦相手の効果によって新たに能力を得られない。@@を得る。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《散散　緑姫》がいる場合、パワーがこのシグニのパワーの半分以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A %X：ターン終了時まで、このシグニのパワーを＋5000する。" +
                "~#：カードを１枚引き【エナチャージ２】をする。"
        );

        setName("en", "Lupal//Dissona, Phantom Beast Deity");
        setDescription("en",
                "@C: As long as this SIGNI has power 30000 or more, it gains@>@C: This SIGNI cannot gain abilities by your opponent's effects.@@" +
                "@U: Whenever this SIGNI attacks, if \"Midoriko, Dispersal Three\" is on your field, vanish target SIGNI on your opponent's field with power that is half of this SIGNI's power or less.\n" +
                "@A %X: This SIGNI gets +5000 power until end of turn." +
                "~#Draw a card and [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Wolfriend//Dissona, Phantom Beast Deity");
        setDescription("en_fan",
                "@C: As long as this SIGNI's power is 30000 or more, it gains:" +
                "@>@C: This SIGNI can't gain new abilities by your opponent's effects.@@" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Midoriko, Utterly\", target 1 of your opponent's SIGNI with power equal to or less than half this SIGNI's power, and banish it.\n" +
                "@A %X: Until end of turn, this SIGNI gets +5000 power." +
                "~#Draw 1 card, and [[Ener Charge 2]]."
        );

		setName("zh_simplified", "幻兽神 缠缚//失调");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量在30000以上时，得到\n" +
                "@>@C :这只精灵不会因为对战对手的效果新得到能力。@@\n" +
                "@U :当这只精灵攻击时，你的场上有《散散　緑姫》的场合，力量在这只精灵的力量的一半以下的对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A %X:直到回合结束时为止，这只精灵的力量+5000。" +
                "~#抽1张牌并[[能量填充2]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(10000);

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

            registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getPower().getValue() >= 30000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ABILITY_BE_ATTACHED, this::onAttachedConstEffRuleCheck));
        }
        private RuleCheckState onAttachedConstEffRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility().getSourceAttachAbility() != null &&
                   !isOwnCard(data.getSourceAbility().getSourceAttachAbility().getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onAutoEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("散散　緑姫"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, getPower().getValue()/2)).get();
                banish(target);
            }
        }

        private void onActionEff()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            enerCharge(2);
        }
    }
}
