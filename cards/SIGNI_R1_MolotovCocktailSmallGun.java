package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

import java.util.Objects;

public final class SIGNI_R1_MolotovCocktailSmallGun extends Card {

    public SIGNI_R1_MolotovCocktailSmallGun()
    {
        setImageSets("WXDi-P15-087", "SPDi10-16");

        setOriginalName("小砲　カエンビン");
        setAltNames("ショウホウカエンビン Shouhou Kaenbin");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのセンタールリグのレベルが対戦相手のセンタールリグと同じ場合、対戦相手のパワー5000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Molotov Cocktail, Small Cannon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if your Center LRIG and your opponent's Center LRIG are the same level, you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Molotov Cocktail, Small Gun");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if you center LRIG is the same level as your opponent's center LRIG, target 1 of your opponent's SIGNI with power 5000 or less, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "小炮 燃烧瓶");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的核心分身的等级与对战对手的核心分身相同的场合，对战对手的力量5000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(Objects.equals(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue(), getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue()))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
