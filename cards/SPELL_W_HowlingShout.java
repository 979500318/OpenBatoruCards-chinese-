package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_W_HowlingShout extends Card {

    public SPELL_W_HowlingShout()
    {
        setImageSets("WXDi-P09-055");
        setLinkedImageSets("WXDi-P09-038","WXDi-P09-058","WXDi-P09-073");

        setOriginalName("ハウリング・シャウト");
        setAltNames("ハウリングシャウト Hauringu Shauto");
        setDescription("jp",
                "あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋5000する。それが《コードハート　ＬＩＯＮ//メモリア》か《幻獣　ＬＯＶＩＴ//メモリア》か《爆砲　ＷＯＬＦ//メモリア》の場合、それは覚醒する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Howling Shout");
        setDescription("en",
                "Target SIGNI on your field gets +5000 power until the end of your opponent's next end phase. If it is a \"LION//Memoria, Code: Heart\", \"LOVIT//Memoria, Phantom Beast\", or \"WOLF//Memoria, Explosive Gun\", awaken it." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Howling Shout");
        setDescription("en_fan",
                "Target 1 of your SIGNI, and until the end of your opponent's next turn, it gets +5000 power. If that SIGNI is \"Code Heart LION//Memoria\", \"LOVIT//Memoria, Phantom Beast\", or \"WOLF//Memoria, Explosive Gun\", awaken it." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "长嚎·呼喊");
        setDescription("zh_simplified", 
                "你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+5000。其是《コードハート　LION//メモリア》或《幻獣　LOVIT//メモリア》或《爆砲　WOLF//メモリア》的场合，将其觉醒。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                gainPower(spell.getTarget(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                
                if(spell.getTarget().getIndexedInstance().getName().getValue().contains("コードハート　LION//メモリア") ||
                   spell.getTarget().getIndexedInstance().getName().getValue().contains("幻獣　LOVIT//メモリア") ||
                   spell.getTarget().getIndexedInstance().getName().getValue().contains("爆砲　WOLF//メモリア"))
                {
                    spell.getTarget().getIndexedInstance().getCardStateFlags().addValue(CardStateFlag.AWAKENED);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
