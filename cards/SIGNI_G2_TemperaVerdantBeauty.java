package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_TemperaVerdantBeauty extends Card {

    public SIGNI_G2_TemperaVerdantBeauty()
    {
        setImageSets("WX24-P3-084");

        setOriginalName("翠美　テンペラ");
        setAltNames("スイビテンペラ Suibi Tenpera");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのトラッシュから＜美巧＞のシグニを１枚まで対象とし、それをデッキの一番下に置く。その後、この方法でデッキに移動したシグニと同じ名前のあなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋5000する。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Tempera, Verdant Beauty");
        setDescription("en",
                "@U: At the end of your turn, target up to 1 <<Beautiful Technique>> SIGNI from your trash, and put it on the bottom of your deck. Then, target 1 of your SIGNI with the same name as the SIGNI put into your deck this way, and until the end of your opponent's next turn, it gets +5000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "翠美 蛋彩画");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，从你的废弃区把<<美巧>>精灵1张最多作为对象，将其放置到牌组最下面。然后，与这个方法往牌组移动的精灵相同名字的你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+5000。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromTrash()).get();
            
            if(target != null)
            {
                String name = target.getIndexedInstance().getName().getValue();
                if(returnToDeck(target, DeckPosition.BOTTOM))
                {
                    CardIndex targetSecond = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withName(name)).get();
                    gainPower(targetSecond, 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
