package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_PinaAsahina extends Card {

    public SIGNI_G1_PinaAsahina()
    {
        setImageSets("WXDi-CP02-085");

        setOriginalName("朝比奈フィーナ");
        setAltNames("アサヒナフィーナ Asahina Fiina");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたのエナゾーンにカード１枚が置かれたとき、ターン終了時まで、このシグニのパワーを＋3000する。\n" +
                "@U：このシグニがアタックしたとき、このシグニのパワーが5000以上であなたの場に他の＜ブルアカ＞のシグニがある場合、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Asahina Pina");
        setDescription("en",
                "@U $T1: During your turn, when a card is put into your Ener Zone, this SIGNI gets +3000 power until end of turn.\n@U: Whenever this SIGNI attacks, if this SIGNI's power is 5000 or more and there is another <<Blue Archive>> SIGNI on your field, vanish target SIGNI on your opponent's field with power 2000 or less.~{{C: This SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Pina Asahina");
        setDescription("en_fan",
                "@U $T1: During your turn, when a card is put into your ener zone, until end of turn, this SIGNI gets +3000 power.\n" +
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 5000 or more and you have another <<Blue Archive>> SIGNI on the field, target 1 of your opponent'S SIGNI with power 2000 or less, and banish it." +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "朝比奈菲娜");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当你的能量区有1张牌放置时，直到回合结束时为止，这只精灵的力量+3000。\n" +
                "@U :当这只精灵攻击时，这只精灵的力量在5000以上且你的场上有其他的<<ブルアカ>>精灵的场合，对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.ENER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(getCardIndex(), 3000, ChronoDuration.turnEnd());
        }
        
        private void onAutoEff2()
        {
            if(getPower().getValue() >= 5000 && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                banish(target);
            }
        }
    }
}
