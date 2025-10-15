package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W2_HasumiHanekawa extends Card {

    public SIGNI_W2_HasumiHanekawa()
    {
        setImageSets("WXDi-CP02-071");

        setOriginalName("羽川ハスミ");
        setAltNames("ハネカワハスミ Hanekawa Hasumi");
        setDescription("jp",
                "@C：あなたの場に他の＜ブルアカ＞のシグニがあるかぎり、対戦相手のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Hanekawa Hasumi");
        setDescription("en",
                "@C: As long as there is another <<Blue Archive>> SIGNI on your field, if a SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put into the trash instead of the Ener Zone.~{{C: This SIGNI gets +4000 power.@@" +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Hasumi Hanekawa");
        setDescription("en_fan",
                "@C: As long as there is another <<Blue Archive>> SIGNI on your field, if this SIGNI would banish your opponent's SIGNI in battle, it is put into the trash instead of the ener zone." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Look at the top 3 cards of your deck. Reveal 1 card from among them, and add it to your hand, or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "羽川莲见");
        setDescription("zh_simplified", 
                "@C :你的场上有其他的<<ブルアカ>>精灵时，对战对手的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEff1Cond, new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEff1ModOverrideCond, this::onConstEff1ModOverrideHandler)
            ));

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEff1ModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && !isOwnCard(event.getCallerCardIndex());
        }
        private void onConstEff1ModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            
            if(reveal(cardIndex))
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
