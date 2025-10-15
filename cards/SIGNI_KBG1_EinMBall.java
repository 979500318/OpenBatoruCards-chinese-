package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;

public final class SIGNI_KBG1_EinMBall extends Card {

    public SIGNI_KBG1_EinMBall()
    {
        setImageSets("WXDi-P16-093");

        setOriginalName("アイン＝M・ボール");
        setAltNames("アインエムボール Ain Emu Booru M-Ball");
        setDescription("jp",
                "=T ＜DIAGRAM＞\n" +
                "^U：あなたのアタックフェイズ開始時、対戦相手はカードを１枚引き、あなたは対戦相手の手札を１枚見ないで選び、捨てさせる。この効果で捨てられたシグニのレベル１につき対戦相手のデッキの上からカードを１枚トラッシュに置く。この効果でスペルが捨てられた場合、【エナチャージ１】をする。\n\n" +
                "@C：あなたの場に＜DIAGRAM＞のルリグが３体いないかぎり、このカードはすべての領域で色を失う。"
        );

        setName("en", "M - Ball, Type: Eins");
        setDescription("en",
                "=T <<DIAGRAM>>\n^U: At the beginning of your attack phase, your opponent draws a card and discards a card at random. Put the top card of your opponent's deck into their trash for each level of the SIGNI discarded with this effect. If a spell is discarded with this effect, [[Ener Charge 1]].\n\n@C: This card loses its colors in all zones unless there are three <<DIAGRAM>> LRIG on your field."
        );
        
        setName("en_fan", "Ein-M Ball");
        setDescription("en_fan",
                "=T <<DIAGRAM>>\n" +
                "^U: At the beginning of your attack phase, your opponent draws 1 card, and you choose 1 card from your opponent's hand without looking, and your opponent discards it. For each level of the SIGNI discarded this way, put the top card of your opponent's deck into the trash. If a spell was discarded this way, [[Ener Charge 1]].\n\n" +
                "@C: If there aren't 3 <<DIAGRAM>> LRIG on your field, this SIGNI loses all of its colors in all zones."
        );

		setName("zh_simplified", "EINS=M·球");
        setDescription("zh_simplified", 
                "=T<<DIAGRAM>>\n" +
                "^U:你的攻击阶段开始时，对战对手抽1张牌，你不看对战对手的手牌选1张，舍弃。依据这个效果舍弃的精灵的等级的数量，每有1级就从对战对手的牌组上面把1张牌放置到废弃区。这个效果把魔法舍弃的场合，[[能量填充1]]。\n" +
                "@C :你的场上的<<DIAGRAM>>分身没有在3只时，这张牌在全部的领域的颜色失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK, CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(2000);

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

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new ModifiableBaseValueModifier<>(this::onConstEffModGetSample, () -> CardDataColor.EMPTY_VALUE));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
        }

        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.DIAGRAM) &&
                   isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(getOpponent(), 1);
            
            CardIndex cardIndex = playerChoiceHand().get();
            if(discard(cardIndex).get() != null)
            {
                if(CardType.isSIGNI(cardIndex.getCardReference().getType()))
                {
                    millDeck(getOpponent(), cardIndex.getIndexedInstance().getLevel().getValue());
                } else if(cardIndex.getCardReference().getType() == CardType.SPELL)
                {
                    enerCharge(1);
                }
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isLRIGTeam(CardLRIGTeam.DIAGRAM) ? ConditionState.OK : ConditionState.BAD;
        }
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
    }
}
