package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_CodeAntiMadokaFessone extends Card {

    public SIGNI_B1_CodeAntiMadokaFessone()
    {
        setImageSets("WXDi-P14-059");

        setOriginalName("コードアンチ　マドカ//フェゾーネ");
        setAltNames("コードアンチマドカフェゾーネ Koodo Anchi Madoka Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、カードを１枚引き、手札を１枚捨てる。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それを凍結する。"
        );

        setName("en", "Madoka//Fesonne, Code: Anti");
        setDescription("en",
                "@U: At the beginning of your attack phase, draw a card and discard a card.\n@E: Freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code Anti Madoka//Fessone");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, draw 1 card, and discard 1 card from your hand.\n" +
                "@E: Target 1 of your opponent's SIGNI, and freeze it."
        );

		setName("zh_simplified", "古兵代号 円//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，抽1张牌，手牌1张舍弃。\n" +
                "@E :对战对手的精灵1只作为对象，将其冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
            discard(1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
    }
}
