package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_TsurugiKenzaki extends Card {

    public SIGNI_W3_TsurugiKenzaki()
    {
        setImageSets("WXDi-CP02-072");

        setOriginalName("剣先ツルギ");
        setAltNames("ケンザキツルギ Kenzaki Tsurugi");
        setDescription("jp",
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、それを手札に加える。" +
                "~{{C：対戦相手のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Kenzaki Tsurugi");
        setDescription("en",
                "@U: Whenever this SIGNI vanishes a SIGNI through battle, you may discard a <<Blue Archive>> card. If you do, add target SIGNI with a #G from your trash to your hand.~{{C: If a SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put into the trash instead of the Ener Zone.@@" +
                "~#Choose one -- \n$$1Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2Draw a card."
        );
        
        setName("en_fan", "Tsurugi Kenzaki");
        setDescription("en_fan",
                "@U: Whenever this SIGNI banishes a SIGNI in battle, target 1 #G @[Guard]@ SIGNI from your trash, and you may discard 1 <<Blue Archive>> card from your hand. If you do, add it to your hand." +
                "~{{C: If this SIGNI would banish your opponent's SIGNI in battle, it is put into the trash instead of the ener zone.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "剑先鹤城");
        setDescription("zh_simplified", 
                "@U 当这只精灵因为战斗把精灵1只破坏时，从你的废弃区把持有#G的精灵1张作为对象，可以从手牌把<<ブルアカ>>牌1张舍弃。这样做的场合，将其加入手牌。\n" +
                "~{{C:对战对手的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ConstantAbility cont = registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideCond, this::onConstEffModOverrideHandler)
            ));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            
            if(target != null && discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
            {
                addToHand(target);
            }
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && !isOwnCard(event.getCallerCardIndex());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
