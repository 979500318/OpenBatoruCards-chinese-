package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

import java.util.stream.IntStream;

public final class SPELL_B_TEMPOUP extends Card {

    public SPELL_B_TEMPOUP()
    {
        setImageSets("WXDi-P14-061");
        setLinkedImageSets("WXDi-P14-045");

        setOriginalName("TEMPO UP");
        setAltNames("テンポアップ Tenpo Appu");
        setDescription("jp",
                "あなたの青のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のセンタールリグのレベル以下の数字１つを宣言する。対戦相手の手札を見て、#Gを持たず宣言した数字と同じレベルを持つすべてのシグニを捨てさせる。@@を得る。それが《コードハート　ピルルク//フェゾーネ》の場合、それは覚醒する。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "TEMPO UP");
        setDescription("en",
                "Target blue SIGNI on your field gains@>@U: Whenever this SIGNI attacks, declare a number less than or equal to the level of your opponent's Center LRIG. Look at your opponent's hand and your opponent discards all SIGNI without a #G that are the same level as the declared number.@@until end of turn. If it is a \"Piruluk//Fesonne, Code: Heart\", awaken it. " +
                "~#Down up to two target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "TEMPO UP");
        setDescription("en_fan",
                "Target 1 of your blue SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, declare 1 number equal to or less than the level of your opponent's center LRIG. Look at your opponent's hand, and your opponent discards all SIGNI without #G @[Guard]@ with level equal to the declared number.@@" +
                "If that SIGNI is \"Code Heart Piruluk//Fessone\", it awakens." +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "TEMPO　UP");
        setDescription("zh_simplified", 
                "你的蓝色的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，对战对手的核心分身的等级以下的数字1种宣言。看对战对手的手牌，把不持有#G的持有与宣言数字相同等级的全部的精灵舍弃。@@\n" +
                "。其是《コードハート　ピルルク//フェゾーネ》的场合，将其觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#对战对手的精灵2只最多作为对象，将这些#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.BLUE)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());

                if(spell.getTarget() != null && spell.getTarget().getIndexedInstance().getName().getValue().contains("コードハート　ピルルク//フェゾーネ"))
                {
                    spell.getTarget().getIndexedInstance().getCardStateFlags().addValue(CardStateFlag.AWAKENED);
                }
            }
        }
        private void onAttachedAutoEff()
        {
            int[] options = IntStream.rangeClosed(0, getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue()).toArray();
            int arrayId = playerChoiceNumber(options)-1;
            
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            getAbility().getSourceCardIndex().getIndexedInstance().discard(new TargetFilter().OP().SIGNI().withLevel(options[arrayId]).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromRevealed().getExportedData());
            
            addToHand(getCardsInRevealed(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}
