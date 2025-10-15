package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

import java.util.List;

public final class SIGNI_G3_TriceratopsPhantomDragon extends Card {

    public SIGNI_G3_TriceratopsPhantomDragon()
    {
        setImageSets("WX25-P1-096");

        setOriginalName("幻竜　トリケラトプス");
        setAltNames("ゲンリュウトリケラトプス Genryuu Torikeratopusu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー10000以上のシグニ１体を対象とし、あなたのエナゾーンから共通するクラスを持たないシグニ２枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Triceratops, Phantom Dragon");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 10000 or more, and you may put 2 SIGNI that do not share a common class from your ener zone into the trash. If you do, banish it."
        );

		setName("zh_simplified", "龙兽 三角龙");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量10000以上的精灵1只作为对象，可以从你的能量区把不持有共通类别的精灵2张放置到废弃区。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(3);
        setPower(10000);

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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().fromEner(), this::onAutoEffTargetCond);
                
                if(trash(data) == 2)
                {
                    banish(target);
                }
            }
        }
        private boolean onAutoEffTargetCond(List<CardIndex> pickedCards)
        {
            return pickedCards.isEmpty() || (pickedCards.size() == 2 && !pickedCards.getFirst().getIndexedInstance().getSIGNIClass().matches(pickedCards.getLast().getIndexedInstance().getSIGNIClass()));
        }
    }
}
