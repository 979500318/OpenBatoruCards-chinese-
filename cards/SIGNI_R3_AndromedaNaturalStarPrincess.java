package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_AndromedaNaturalStarPrincess extends Card {

    public SIGNI_R3_AndromedaNaturalStarPrincess()
    {
        setImageSets("WXDi-P10-036");

        setOriginalName("羅星姫　アンドロメダ");
        setAltNames("ラセイキアンドロメダ Raseiki Andoromeda");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つからあなたの場にあるレベル１のシグニ１体につき１つ選ぶ。\n" +
                "$$1対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "$$2あなたのレベル１のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋10000する。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Andromeda, Galactic Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, choose one of the following for each level one SIGNI on your field.\n" +
                "$$1 If there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "$$2 Target level one SIGNI on your field gets +10000 power until the end of your opponent's next end phase." +
                "~#Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Andromeda, Natural Star Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, for each of your level 1 SIGNI, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "$$2 Target 1 of your level 1 SIGNI, and until the end of your opponent's next turn, it gets +10000 power." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "罗星姬 仙女座");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种依据你的场上的等级1的精灵的数量，每有1只就选1种。\n" +
                "$$1 对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "$$2 你的等级1的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+10000。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int count = Math.min(2, new TargetFilter().own().SIGNI().withLevel(1).getValidTargetsCount());
            
            if(count > 0)
            {
                int modes = playerChoiceMode(count,count);
                
                if((modes & 1) != 0 && getEnerCount(getOpponent()) >= 2)
                {
                    CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                    trash(cardIndex);
                }
                if((modes & 2) != 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withLevel(1)).get();
                    gainPower(target, 10000, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
