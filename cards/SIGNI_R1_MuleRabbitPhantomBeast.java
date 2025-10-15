package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R1_MuleRabbitPhantomBeast extends Card {

    public SIGNI_R1_MuleRabbitPhantomBeast()
    {
        setImageSets("WXDi-P16-066");

        setOriginalName("幻獣　ラバピカ");
        setAltNames("ゲンジュウラバピカ Genjuu Rabapika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。" +
                "~#：手札を１枚捨て、カードを３枚引く。"
        );

        setName("en", "Mule Hare, Phantom Terra Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your field gets +5000 power until end of turn." +
                "~#Discard a card and draw three cards. "
        );
        
        setName("en_fan", "Mule Rabbit, Phantom Beast");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your SIGNI, and until end of turn, it gets +5000 power." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "幻兽 拉巴皮卡");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(3000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}
