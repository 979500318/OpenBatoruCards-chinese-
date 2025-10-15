package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_OberonVerdantAngelPrincess extends Card {

    public SIGNI_G3_OberonVerdantAngelPrincess()
    {
        setImageSets("WXDi-P11-045");

        setOriginalName("翠天姫　オーベロン");
        setAltNames("スイテンキオーベロン Suitenki Ooberon");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜天使＞のシグニが３枚以上あるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのトラッシュから＜天使＞のシグニ１枚を対象とし、それをエナゾーンに置く。\n" +
                "@U：対戦相手のターンの間、このシグニが場を離れたとき、あなたのエナゾーンから＜天使＞のシグニを１枚まで対象とし、それを手札に加える。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Oberon, Jade Angel Queen");
        setDescription("en",
                "@C: As long as there are three or more <<Angel>> SIGNI in your Ener Zone, this SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your attack phase, put target <<Angel>> SIGNI from your trash into your Ener Zone.\n" +
                "@U: When this SIGNI leaves the field during your opponent's turn, add up to one target <<Angel>> SIGNI from your Ener Zone to your hand." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Oberon, Verdant Angel Princess");
        setDescription("en_fan",
                "@C: As long as there are 3 or more <<Angel>> SIGNI in your ener zone, this SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your attack phase, target 1 <<Angel>> SIGNI from your trash, and put it into the ener zone.\n" +
                "@U: During your opponent's turn, when this SIGNI leaves the field, target up to 1 <<Angel>> SIGNI from your ener zone, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "翠天姬 奥布朗");
        setDescription("zh_simplified", 
                "@C :你的能量区的<<天使>>精灵在3张以上时，这只精灵的力量+3000。\n" +
                "@U :你的攻击阶段开始时，从你的废弃区把<<天使>>精灵1张作为对象，将其放置到能量区。\n" +
                "@U :对战对手的回合期间，当这只精灵离场时，从你的能量区把<<天使>>精灵1张最多作为对象，将其加入手牌。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
            putInEner(target);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
