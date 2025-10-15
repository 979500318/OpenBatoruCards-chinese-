package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G1_MarblingVerdantBeauty extends Card {

    public SIGNI_G1_MarblingVerdantBeauty()
    {
        setImageSets("WXDi-P09-071");

        setOriginalName("翠美　マーブリング");
        setAltNames("スイビマーブリング Suibi Maaburingu");
        setDescription("jp",
                "@U：あなたのメインフェイズ以外でこのシグニがバニッシュされたとき、あはたのシグニ１体を対象とし、ターン終了時まで、@>@C：バニッシュされない。@@を得る。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Marbling, Jade Beauty");
        setDescription("en",
                "@U: When this SIGNI is vanished outside of your main phase, target SIGNI on your field gains@>@C: This SIGNI cannot be vanished.@@until end of turn." +
                "~#You may pay %X. If you do, vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Marbling, Verdant Beauty");
        setDescription("en_fan",
                "@U: When this SIGNI is banished other than during your main phase, target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't be banished.@@" +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "翠美 墨流画");
        setDescription("zh_simplified", 
                "@U :当在你的主要阶段以外把这只精灵破坏时，你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不会被破坏。@@" +
                "~#对战对手的力量7000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() || getCurrentPhase() != GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, data -> RuleCheckState.BLOCK));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
