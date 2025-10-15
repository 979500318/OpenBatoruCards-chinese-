package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R3_HeavyMachineGunRoaringGun extends Card {

    public SIGNI_R3_HeavyMachineGunRoaringGun()
    {
        setImageSets("WXDi-P16-049", "SPDi02-27");

        setOriginalName("轟砲　ヘビーマシンガン");
        setAltNames("ゴウホウヘビーマシンガン Gouhou Hebii Mashin Gan");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのライフクロスが３枚以上ある場合、対戦相手のパワー7000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2あなたのライフクロスが２枚以下の場合、対戦相手のパワー12000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Heavy Machine Gun, Roaring Cannon");
        setDescription("en",
                "@U: At the beginning of your attack phase, choose one of the following.\n$$1If you have three or more cards in your Life Cloth, vanish target SIGNI on your opponent's field with power 7000 or less.\n$$2If you have two or fewer cards in your Life Cloth, you may pay %R. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Heavy Machine Gun, Roaring Gun");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If you have 3 or more life cloth, target 1 of your opponent's SIGNI with power 7000 or less, and banish it.\n" +
                "$$2 If you have 2 or less life cloth, target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R. If you do, banish it."
        );

		setName("zh_simplified", "轰炮 重机枪");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 你的生命护甲在3张以上的场合，对战对手的力量7000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 你的生命护甲在2张以下的场合，对战对手的力量12000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                if(getLifeClothCount(getOwner()) >= 3)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,7000)).get();
                    banish(target);
                }
            } else if(getLifeClothCount(getOwner()) <= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                
                if(target != null && payEner(Cost.color(CardColor.RED, 1)))
                {
                    banish(target);
                }
            }
        }
    }
}
