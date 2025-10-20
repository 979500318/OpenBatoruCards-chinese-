package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G2_EuphoniumVerdantBeauty extends Card {

    public SIGNI_G2_EuphoniumVerdantBeauty()
    {
        setImageSets("WX25-P1-093");

        setOriginalName("翠美　ユーフォニアム");
        setAltNames("スイビユーフォニアム Suibi Yuufoniumu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー8000以下のシグニ１体を対象とし、あなたのエナゾーンからそれと共通するクラスを持つシグニ１枚をトラッシュに置きアップ状態のこのシグニをダウンしてもよい。そうした場合、それをバニッシュする。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Euphonium, Verdant Beauty");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 8000 or less, and you may put 1 SIGNI that shares a common class with that SIGNI from your ener zone into the trash and down this upped SIGNI. If you do, banish it." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "翠美 上低音号");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量8000以下的精灵1只作为对象，可以从你的能量区把持有与其共通类别的精灵1张放置到废弃区且把竖直状态的这只精灵横置。这样做的场合，将其破坏。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && !isState(CardStateFlag.DOWNED) &&
               payAll(new TrashCost(new TargetFilter().own().SIGNI().withClass(target.getIndexedInstance().getSIGNIClass()).fromEner()), new DownCost()))
            {
                banish(target);
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
