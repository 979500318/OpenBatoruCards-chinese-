package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.cost.MillCost;
import open.batoru.data.ability.modifiers.AttackModifier;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;

public final class LRIG_W3_YukiUnbloomedMiko extends Card {

    public LRIG_W3_YukiUnbloomedMiko()
    {
        setImageSets("WXDi-P12-006", "WXDi-P12-006U");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET);

        setOriginalName("未開の巫女　ユキ");
        setAltNames("ミカイノミコユキ Mikai no Miko Yuki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に#Sのシグニが２体以上ある場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2対戦相手のシグニ１体を対象とし、このターン終了時、それをデッキの一番下に置く。\n" +
                "@E：あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A @[エクシード４]@：【シグニバリア】１つを得る。"
        );

        setName("en", "Yuki, Savage Miko");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are two or more #S SIGNI on your field, choose one of the following.\n" +
                "$$1 Return target level one SIGNI on your opponent's field to its owner's hand.\n" +
                "$$2 Choose target SIGNI on your opponent's field. At the end of this turn, put it on the bottom of its owner's deck.\n" + 
                "@E: Look at the top five cards of your deck. Add up to one card from among them to your hand. Put the rest on the bottom of your deck in any order.\n" +
                "@A @[Exceed 4]@: Gain a [[SIGNI Barrier]]."
        );
        
        setName("en_fan", "Yuki, Unbloomed Miko");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 2 or more #S @[Dissona]@ SIGNI on your field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's level 1 SIGNI, and return it to their hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and at the end of this turn, put it on the bottom of their deck.\n" +
                "@E: Look at the top 5 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "@A @[Exceed 4]@: Gain 1 [[SIGNI Barrier]]."
        );
        
		setName("zh_simplified", "未开的巫女 雪");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的#S的精灵在2只以上的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的等级1的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 对战对手的精灵1只作为对象，这个回合结束时，将其放置到牌组最下面。\n" +
                "@E :从你的牌组上面看5张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@A @[超越 4]@:得到[[精灵屏障]]1个。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

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
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() >= 2)
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                    addToHand(target);
                } else {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                    
                    if(target != null)
                    {
                        int instanceId = target.getIndexedInstance().getInstanceId();
                        callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                            if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId) returnToDeck(target, DeckPosition.BOTTOM);
                        });
                    }
                }
            }
        }
        
        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilitySIGNIBarrier(), ChronoDuration.permanent());
        }
    }
}
