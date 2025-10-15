package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_W_RhythmicFortune extends Card {

    public SPELL_W_RhythmicFortune()
    {
        setImageSets("WXDi-P13-061");

        setOriginalName("リズミック・フォーチュン");
        setAltNames("リズミックフォーチュン Rizumikku Foochun");
        setDescription("jp",
                "あなたの#Sのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000し、それは@>@U：対戦相手のターン終了時、カードを１枚引くか【エナチャージ１】をする。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Rhythmic Fortune");
        setDescription("en",
                "Target #S SIGNI on your field gets +3000 power and gains@>@U: At the end of your opponent's turn, draw a card or [[Ener Charge 1]].@@until the end of your opponent's next end phase." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Rhythmic Fortune");
        setDescription("en_fan",
                "Target 1 of your #S @[Dissona]@ SIGNI, and until the end of your opponent's next turn, it gets +3000 power, and it gains:" +
                "@>@U: At the end of your opponent's turn, draw 1 card or [[Ener Charge 1]].@@" +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "循环·强运");
        setDescription("zh_simplified", 
                "你的#S的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000，其得到\n" +
                "@>@U :对战对手的回合结束时，抽1张牌或[[能量填充1]]。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().dissona()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                gainPower(spell.getTarget(), 3000, ChronoDuration.nextTurnEnd(getOpponent()));

                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private ConditionState onAttachedAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().draw(1);
            } else {
                getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
