package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.events.EventTarget;

public final class SPELL_B_LIVELYLIVE extends Card {

    public SPELL_B_LIVELYLIVE()
    {
        setImageSets("WXDi-P09-069");
        setLinkedImageSets("WXDi-P09-042","WXDi-P09-052","WXDi-P09-081");

        setOriginalName("LIVELY LIVE");
        setAltNames("ライブリーライブ Raiburii Raibu");
        setDescription("jp",
                "あなたの《羅菌姫　みこみこ//メモリア》か《中罠　ゆかゆか//メモリア》か《羅原　まほまほ//メモリア》カード青のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@U $T1：このシグニが対戦相手の、能力か効果の対象になったとき、対戦相手の手札を１枚見ないで選び、捨てさせる@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "LIVELY LIVE");
        setDescription("en",
                "Target \"Mikomiko//Memoria, Bacteria Queen\", \"Yukayuka//Memoria, Medium Trickster\", \"Mahomaho//Memoria, Natural Element\", or blue SIGNI on your field gains@>@U $T1: When this SIGNI becomes the target of your opponent's ability or effect, your opponent discards a card at random.@@until the end of your opponent's next end phase." +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "LIVELY LIVE");
        setDescription("en_fan",
                "Target 1 of your \"Mikomiko//Memoria, Natural Bacteria Queen\", \"Yukayuka//Memoria, Medium Trap\", or \"Mahomaho//Memoria, Natural Source\", or 1 of your blue SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@U $T1: When this SIGNI is targeted by your opponent's ability of effect, choose 1 card from your opponent's hand without looking, and discard it.@@" +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "LIVELY LIVE");
        setDescription("zh_simplified", 
                "你的《羅菌姫　みこみこ//メモリア》或《中罠　ゆかゆか//メモリア》或《羅原　まほまほ//メモリア》或蓝色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，不看对战对手的手牌选1张，舍弃。@@" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().
                              or(new TargetFilter().withName("羅菌姫　みこみこ//メモリア","中罠　ゆかゆか//メモリア","羅原　まほまほ//メモリア"), new TargetFilter().withColor(CardColor.BLUE))));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.TARGET, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);

                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            getAbility().getSourceCardIndex().getIndexedInstance().discard(cardIndex);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
