package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_Transcription extends Card {

    public SPELL_G_Transcription()
    {
        setImageSets("WX24-P3-086");

        setOriginalName("写本");
        setAltNames("シャホン Shahon");
        setDescription("jp",
                "あなたのエナゾーンから＜美巧＞のシグニ１枚を対象とし、それを場に出す。次の対戦相手のターン終了時まで、それのパワーを＋2000する。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Transcription");
        setDescription("en",
                "Target 1 <<Beautiful Technique>> SIGNI from your ener zone, and put it onto the field. Until the end of your opponent's next turn, it gets +2000 power." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "写本");
        setDescription("zh_simplified", 
                "从你的能量区把<<美巧>>精灵1张作为对象，将其出场。直到下一个对战对手的回合结束时为止，其的力量+2000。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromEner().playable()));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(putOnField(target))
            {
                gainPower(target, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
